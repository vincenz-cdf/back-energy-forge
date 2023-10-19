package com.vinz.energy.EnergyForge.service;

import java.util.ArrayList;
import java.util.List;

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

	    EquippedItem newEquippedItem = new EquippedItem();
	    com.vinz.energy.EnergyForge.db.Character character = characterDao.findById(charId)
	            .orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));

	    Equipment equipment = equipmentDao.findById(gearId)
	            .orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND));

	    // 1. Remove any existing equipped items of the same slot as the new equipment.
	    character.getEquippedItems().removeIf(item -> item.getSlot() == equipment.getSlot());

	    // 2. If equipping a ZWEIHAND, remove LEFTHAND and RIGHTHAND.
	    if (equipment.getSlot() == EquipmentSlot.ZWEIHAND) {
	        character.getEquippedItems().removeIf(item -> item.getSlot() == EquipmentSlot.LEFTHAND || item.getSlot() == EquipmentSlot.RIGHTHAND);
	    }

	    // 3. If equipping a LEFTHAND or RIGHTHAND, remove ZWEIHAND.
	    else if (equipment.getSlot() == EquipmentSlot.LEFTHAND || equipment.getSlot() == EquipmentSlot.RIGHTHAND) {
	        character.getEquippedItems().removeIf(item -> item.getSlot() == EquipmentSlot.ZWEIHAND);
	    }

	    newEquippedItem.setCharacter(character);
	    newEquippedItem.setEquipment(equipment);
	    newEquippedItem.setSlot(equipment.getSlot());
	    equippedItemDao.save(newEquippedItem);

	    return characterService.characterStatsUpdateAfterEquip(charId, equipment, newEquippedItem);
	}


}
