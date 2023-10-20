package com.vinz.energy.EnergyForge.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.ArmorDao;
import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.db.equipments.Weapon;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.CharacterMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CharacterService {

	private final CharacterDao characterDao;
	private final CharacterMapper characterMapper;

	private final ArmorDao armorDao;
	private final WeaponDao weaponDao;

	public List<CharacterDto> showCharacters(Long userId) {
		List<com.vinz.energy.EnergyForge.db.Character> chars = characterDao.findByUserIdOrderByLevelAsc(userId);

		return characterMapper.toCharacterDtos(chars);
	}

	public CharacterDto characterStatsUpdateAfterEquip(Long charId, Equipment equipment, EquippedItem equippedItem) {
	    Character currentChar = findCharacterById(charId);

	    if (equipment.getSlot().isWeapon()) {
	        handleWeaponEquipping(currentChar, equipment);
	    } else {
	        handleArmorEquipping(currentChar, equipment);
	    }

	    currentChar.getEquippedItems().add(equippedItem);
	    characterDao.save(currentChar);

	    return characterMapper.toDto(currentChar);
	}

	private Character findCharacterById(Long charId) {
	    return characterDao.findById(charId)
	            .orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));
	}

	private void handleWeaponEquipping(Character currentChar, Equipment equipment) {
	    Weapon weapon = weaponDao.findByEquipmentId(equipment.getId())
	            .orElseThrow(() -> new AppException("Unknown weapon", HttpStatus.NOT_FOUND));
	    if (weapon.getEquipment().getSlot() != EquipmentSlot.RIGHTHAND) {
	        currentChar.setAtk(weapon.getAtkValue());
	    } else {
	        boolean hasLeftOrZweiHand = currentChar.getEquippedItems().stream()
	                .anyMatch(item -> item.getSlot() == EquipmentSlot.LEFTHAND || item.getSlot() == EquipmentSlot.ZWEIHAND);
	        if (!hasLeftOrZweiHand) {
	            currentChar.setAtk(currentChar.getAtk());
	        }
	    }
	}

	private void handleArmorEquipping(Character currentChar, Equipment equipment) {
	    Armor armor = armorDao.findByEquipmentId(equipment.getId())
	            .orElseThrow(() -> new AppException("Unknown armor", HttpStatus.NOT_FOUND));
	    EquipmentSlot otherPartSlot = equipment.getSlot() == EquipmentSlot.HEAD ? EquipmentSlot.BODY : EquipmentSlot.HEAD;

	    EquippedItem equippedOtherPart = findExistingEquippedItemBySlot(currentChar, otherPartSlot);
	    Integer totalArmorValue = armor.getArmorValue();
	    if (equippedOtherPart != null) {
	        totalArmorValue += findArmorValueByEquippedItemId(equippedOtherPart.getEquipment().getId());
	    }

	    currentChar.setArmor(totalArmorValue);
	    currentChar.setMaxArmor(totalArmorValue);
	}

	private Integer findArmorValueByEquippedItemId(Long itemId) {
	    return armorDao.findByEquipmentId(itemId)
	            .orElseThrow(() -> new AppException("Unknown armor", HttpStatus.NOT_FOUND))
	            .getArmorValue();
	}

	private EquippedItem findExistingEquippedItemBySlot(Character character, EquipmentSlot slot) {
	    return character.getEquippedItems().stream()
	            .filter(ei -> ei.getSlot() == slot)
	            .findFirst().orElse(null);
	}

}
