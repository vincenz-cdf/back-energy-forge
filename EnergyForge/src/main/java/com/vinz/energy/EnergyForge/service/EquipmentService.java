package com.vinz.energy.EnergyForge.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinz.energy.EnergyForge.dao.ArmorDao;
import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.ConsumableDao;
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.dao.EquippedItemDao;
import com.vinz.energy.EnergyForge.dao.UserBuildingDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.UserItemDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.Consumable;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.UserBuilding;
import com.vinz.energy.EnergyForge.db.UserItem;
import com.vinz.energy.EnergyForge.db.enums.ItemType;
import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.db.equipments.Weapon;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.ConsumableDto;
import com.vinz.energy.EnergyForge.dto.equipments.ArmorDto;
import com.vinz.energy.EnergyForge.dto.equipments.WeaponDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.ArmorMapper;
import com.vinz.energy.EnergyForge.mappers.CharacterMapper;
import com.vinz.energy.EnergyForge.mappers.WeaponMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EquipmentService {

	private static final Logger logger = LoggerFactory.getLogger(CharacterService.class);

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

	@Autowired
	private CharacterMapper characterMapper;

	@Autowired
	private ConsumableDao consumableDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserBuildingDao userBuildingDao;

	@Transactional
	public List<ArmorDto> getOwnedArmors(Long userId) {

		List<UserItem> userItems = userItemDao.findByUserIdAndItemType(userId, ItemType.EQUIPMENT);
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

		List<UserItem> userItems = userItemDao.findByUserIdAndItemType(userId, ItemType.EQUIPMENT);
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
	public List<ConsumableDto> getOwnedConsumables(Long userId) {

		List<UserItem> userItems = userItemDao.findByUserIdAndItemType(userId, ItemType.CONSUMABLE);
		List<Consumable> consumables = new ArrayList<Consumable>();

		List<ConsumableDto> consumableDtos = new ArrayList<ConsumableDto>();

		for (UserItem ui : userItems) {
			if (ui.getConsumable() != null) {
				Consumable consumable = consumableDao.findById(ui.getConsumable().getId())
						.orElseThrow(() -> new AppException("Unknown consumable", HttpStatus.NOT_FOUND));
				consumables.add(consumable);
			}
		}

		for (Consumable c : consumables) {
			consumableDtos.add(new ConsumableDto(c));
		}
		return consumableDtos;
	}

	@Transactional
	public CharacterDto equipCharacterWithSpecificGear(Long charId, Long gearId) {

		EquippedItem newEquippedItem = new EquippedItem();
		com.vinz.energy.EnergyForge.db.Character character = characterDao.findById(charId)
				.orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));

		Equipment equipment = equipmentDao.findById(gearId)
				.orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND));

		// Check if the character has already equipped the equipment
		boolean isAlreadyEquipped = character.getEquippedItems().stream()
				.anyMatch(equippedItem -> equippedItem.getEquipment().getId().equals(gearId));

		if (isAlreadyEquipped) {
			throw new AppException("Equipment is already equipped by the character", HttpStatus.CONFLICT);
		}

		// Check if character previously had a ZWEIHAND or LEFTHAND equipped
		boolean hadZwei = character.getEquippedItems().stream()
				.anyMatch(item -> item.getSlot() == EquipmentSlot.ZWEIHAND);

		// 1. Remove any existing equipped items of the same slot as the new equipment.
		character.getEquippedItems().removeIf(item -> item.getSlot() == equipment.getSlot());

		// 2. If equipping a ZWEIHAND, remove LEFTHAND and RIGHTHAND.
		if (equipment.getSlot() == EquipmentSlot.ZWEIHAND) {
			character.getEquippedItems().removeIf(
					item -> item.getSlot() == EquipmentSlot.LEFTHAND || item.getSlot() == EquipmentSlot.RIGHTHAND);
		}

		// 3. If equipping a LEFTHAND or RIGHTHAND, remove ZWEIHAND.
		else if (equipment.getSlot() == EquipmentSlot.LEFTHAND || equipment.getSlot() == EquipmentSlot.RIGHTHAND) {
			character.getEquippedItems().removeIf(item -> item.getSlot() == EquipmentSlot.ZWEIHAND);
		}

		// If equipping a RIGHTHAND and previously had a ZWEIHAND or LEFTHAND, set atk
		// to 0.
		if (equipment.getSlot() == EquipmentSlot.RIGHTHAND && hadZwei) {
			character.setAtk(0);
		}

		newEquippedItem.setCharacter(character);
		newEquippedItem.setEquipment(equipment);
		newEquippedItem.setSlot(equipment.getSlot());
		equippedItemDao.save(newEquippedItem);

		return characterService.characterStatsUpdateAfterEquip(charId, equipment, newEquippedItem);
	}

	public CharacterDto unequipAll(Long charId) {

		List<EquippedItem> equippedItems = equippedItemDao.findByCharacterId(charId);
		for (EquippedItem e : equippedItems) {
			equippedItemDao.delete(e);
		}
		equippedItemDao.flush();

		com.vinz.energy.EnergyForge.db.Character character = characterDao.findById(charId)
				.orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));
		character.setArmor(0);
		character.setMaxArmor(0);
		character.setAtk(8);
		characterDao.save(character);
		return characterMapper.toDto(character);
	}

	@Transactional
	public void buyItem(Long itemId, String type, Long userId) {

	    User user = userDao.findById(userId)
	            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
	    
	    // Deduct coins and remove items from the market item pool based on type.
	    if ("weapon".equals(type) || "armor".equals(type)) {
	        Equipment equipment = equipmentDao.findById(itemId)
	                .orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND));
	        checkFundsAndDeduct(user, equipment.getValue());

	        UserItem userItem = new UserItem(user, ItemType.EQUIPMENT, equipment, null);
	        userItemDao.save(userItem);

	        removeEquipmentFromMarketItemPool(user, itemId, type);
	    } else if ("consumable".equals(type)) {
	        Consumable consumable = consumableDao.findById(itemId.intValue())
	                .orElseThrow(() -> new AppException("Unknown consumable", HttpStatus.NOT_FOUND));
	        checkFundsAndDeduct(user, consumable.getValue());

	        UserItem userItem = new UserItem(user, ItemType.CONSUMABLE, null, consumable);
	        userItemDao.save(userItem);

	        removeConsumableFromMarketItemPool(user, itemId);
	    } else {
	        throw new AppException("Invalid item type", HttpStatus.BAD_REQUEST);
	    }

	    // Persist the user with the updated coin count.
	    userDao.save(user);
	}

	private void checkFundsAndDeduct(User user, int itemValue) {
	    if (itemValue > user.getCoin()) {
	        throw new AppException("Not enough coins", HttpStatus.FORBIDDEN);
	    }
	    user.setCoin(user.getCoin() - itemValue);
	}

	private void removeEquipmentFromMarketItemPool(User user, Long itemId, String type) {
	    UserBuilding ub = userBuildingDao.findByUser_IdAndBuilding_Id(user.getId(), 3);
	    if ("weapon".equals(type)) {
	        ub.getMarketItemPool().getWeapons().removeIf(w -> w.getEquipment().getId().equals(itemId));
	    } else { // type is "armor"
	        ub.getMarketItemPool().getArmors().removeIf(a -> a.getEquipment().getId().equals(itemId));
	    }
	    userBuildingDao.save(ub);
	}

	private void removeConsumableFromMarketItemPool(User user, Long itemId) {
	    UserBuilding ub = userBuildingDao.findByUser_IdAndBuilding_Id(user.getId(), 3);
	    ub.getMarketItemPool().getConsumables().removeIf(c -> c.getId().equals(itemId.intValue()));
	    userBuildingDao.save(ub);
	}


}
