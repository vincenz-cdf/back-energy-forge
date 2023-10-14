package com.vinz.energy.EnergyForge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinz.energy.EnergyForge.dao.ArmorDao;
import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.dao.EquippedItemDao;
import com.vinz.energy.EnergyForge.dao.UserItemDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.UserItem;
import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.db.equipments.Weapon;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.equipments.ArmorDto;
import com.vinz.energy.EnergyForge.dto.equipments.WeaponDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.ArmorMapper;
import com.vinz.energy.EnergyForge.mappers.EquippedItemMapper;
import com.vinz.energy.EnergyForge.mappers.WeaponMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EquipmentService {

	@Autowired
	private ArmorDao armorDao;

	@Autowired
	private WeaponDao weaponDao;

	@Autowired
	private ArmorMapper armorMapper;

	@Autowired
	private WeaponMapper weaponMapper;

	@Autowired
	private CharacterDao characterDao;

	@Autowired
	private EquippedItemDao equippedItemDao;

	@Autowired
	private EquippedItemMapper equippedItemMapper;

	@Autowired
	private CharacterService characterService;

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private UserItemDao userItemDao;

	@Transactional
	public List<ArmorDto> getOwnedArmors(Long userId) {

		List<UserItem> userItems = userItemDao.findByUserId(userId);
		List<Armor> armors = new ArrayList<>();

		for (UserItem ui : userItems) {
			if (!ui.getEquipment().getSlot().isWeapon()) {
				Armor armor = armorDao.findByEquipmentId(ui.getEquipment().getId())
						.orElseThrow(() -> new AppException("Unknown armor", HttpStatus.NOT_FOUND));
				armors.add(armor);
			}
		}

		return armorMapper.toDtoList(armors);
	}

	@Transactional
	public List<WeaponDto> getOwnedWeapons(Long userId) {

		List<UserItem> userItems = userItemDao.findByUserId(userId);
		List<Weapon> weapons = new ArrayList<Weapon>();

		for (UserItem ui : userItems) {
			if (ui.getEquipment().getSlot().isWeapon()) {
				Weapon weapon = weaponDao.findByEquipmentId(ui.getEquipment().getId())
						.orElseThrow(() -> new AppException("Unknown weapon", HttpStatus.NOT_FOUND));
				weapons.add(weapon);
			}
		}

		return weaponMapper.toDtoList(weapons);
	}

	@Transactional
	public CharacterDto equipCharacterWithSpecificGear(Long charId, Long gearId) {

		EquippedItem equippedItem = new EquippedItem();

		com.vinz.energy.EnergyForge.db.Character character = characterDao.findById(charId)
				.orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));

		Equipment equipment = equipmentDao.findById(gearId)
				.orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND));

		equippedItem = findExistingEquippedItemBySlot(character, equipment.getSlot()).orElse(equippedItem);

		equippedItem.setCharacter(character);
		equippedItem.setEquipment(equipment);
		equippedItem.setSlot(equipment.getSlot());

		equippedItemDao.save(equippedItem);

		return characterService.characterStatsUpdateAfterEquip(charId, equipment, equippedItem);

	}

	private Optional<EquippedItem> findExistingEquippedItemBySlot(com.vinz.energy.EnergyForge.db.Character character,
			EquipmentSlot slot) {
		if (slot == EquipmentSlot.BODY || slot == EquipmentSlot.HEAD) {
			return character.getEquippedItems().stream().filter(element -> slot.equals(element.getSlot())).findFirst();
		} else if (slot == EquipmentSlot.LEFTHAND || slot == EquipmentSlot.ZWEIHAND) {
			return character.getEquippedItems().stream().filter(element -> element.getSlot() == EquipmentSlot.LEFTHAND
					|| element.getSlot() == EquipmentSlot.ZWEIHAND).findFirst();
		}

		return null;
	}

}
