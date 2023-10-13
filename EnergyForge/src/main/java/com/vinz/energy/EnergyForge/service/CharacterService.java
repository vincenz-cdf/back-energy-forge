package com.vinz.energy.EnergyForge.service;


import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.ArmorDao;
import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
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

    private final UserDao userDao;
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
	    
	    if(equipment.getSlot().isWeapon()) {
	        handleWeaponEquipping(currentChar, equipment);
	    } else {
	        handleArmorEquipping(currentChar, equipment);
	    }

	    currentChar.getEquippedItems().add(equippedItem);
	    characterDao.save(currentChar);
	    
	    return characterMapper.toDto(currentChar);
	}

	private com.vinz.energy.EnergyForge.db.Character findCharacterById(Long charId) {
	    return characterDao.findById(charId)
	        .orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));
	}

	private void handleWeaponEquipping(com.vinz.energy.EnergyForge.db.Character currentChar, Equipment equipment) {
	    Weapon weapon = weaponDao.findByEquipmentId(equipment.getId())
	        .orElseThrow(() -> new AppException("Unknown weapon", HttpStatus.NOT_FOUND));
	    currentChar.setAtk(weapon.getAtkValue());
	}

	private void handleArmorEquipping(com.vinz.energy.EnergyForge.db.Character currentChar, Equipment equipment) {
	    Armor armor = armorDao.findByEquipmentId(equipment.getId())
	        .orElseThrow(() -> new AppException("Unknown armor", HttpStatus.NOT_FOUND));

	    EquipmentSlot otherPartSlot = (equipment.getSlot().equals(EquipmentSlot.HEAD)) ? EquipmentSlot.BODY : EquipmentSlot.HEAD;

	    EquippedItem equippedOtherPart = findExistingEquippedItemBySlot(currentChar, otherPartSlot).orElse(null);

	    Integer totalArmorValue = armor.getArmorValue();
	    if(equippedOtherPart != null) {
	        totalArmorValue += findArmorValueByEquippedItemId(equippedOtherPart.getId());
	    }

	    currentChar.setArmor(totalArmorValue);
	    currentChar.setMaxArmor(totalArmorValue);
	}

	private Integer findArmorValueByEquippedItemId(Long itemId) {
	    return armorDao.findByEquipmentId(itemId)
	        .orElseThrow(() -> new AppException("Unknown armor", HttpStatus.NOT_FOUND))
	        .getArmorValue();
	}

	
	private Optional<EquippedItem> findExistingEquippedItemBySlot(com.vinz.energy.EnergyForge.db.Character character, EquipmentSlot slot) {
	    return character.getEquippedItems().stream()
	            .filter(element -> slot.equals(element.getSlot()))
	            .findFirst();
	}
}
