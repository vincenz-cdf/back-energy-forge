package com.vinz.energy.EnergyForge.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.ArmorDao;
import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.ConsumableDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.UserItemDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.Consumable;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.UserItem;
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

	private static final Logger logger = LoggerFactory.getLogger(CharacterService.class);

	private final CharacterDao characterDao;
	private final CharacterMapper characterMapper;

	private final ArmorDao armorDao;
	private final WeaponDao weaponDao;

	private final UserDao userDao;

	private final ConsumableDao consumableDao;

	private final UserItemDao userItemDao;

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

	public void healCharacterFromTemple(Long id, Long userId) {
		Character character = findCharacterById(id);
		User user = userDao.findById(userId).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

		if (character.getHp() == 0) {
			if (user.getEnergy() < 8) {
				throw new AppException("Not enough energy", HttpStatus.FORBIDDEN);
			} else {
				user.setEnergy(user.getEnergy() - 8);
				character.setRecovering(true);
				character.setHealingPower(1);
				character.setHealingStack(1000);
				character.setHp(1);
				characterDao.save(character);
			}
		} else {
			Integer recoveringCost = (character.getMaxHp() - character.getHp()) * 2;

			if (user.getCoin() < recoveringCost) {
				throw new AppException("Not enough coins", HttpStatus.FORBIDDEN);
			} else {
				user.setCoin(user.getCoin() - recoveringCost);
				character.setHp(character.getMaxHp());
				characterDao.save(character);
			}
		}

	}

	public void drinkCharacterFromTavern(Long id, Long userId) {
		Character character = findCharacterById(id);
		User user = userDao.findById(userId).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

		Integer recoveringCost = character.getLevel() + 3 * 2;

		if (user.getCoin() < recoveringCost) {
			throw new AppException("Not enough coins", HttpStatus.FORBIDDEN);
		} else {
			user.setCoin(user.getCoin() - recoveringCost);
			character.setMorale(character.getMorale() + 2);
			if (character.getMorale() > character.getMaxMorale()) {
				character.setMorale(character.getMaxMorale());
			}
			characterDao.save(character);
		}

	}

	public CharacterDto setCharacterRecoverHeal(Integer id, Long charId, Long userId) {

		Character character = findCharacterById(charId);
		Consumable consumable = consumableDao.findById(id)
				.orElseThrow(() -> new AppException("Unknown consumable", HttpStatus.NOT_FOUND));

		if (character.getRecovering()) {
			throw new AppException("Character already recovering", HttpStatus.FORBIDDEN);
		} else {
			character.setHealingPower(consumable.getHealingPower());
			character.setHealingStack(consumable.getHealingAmount());
			character.setRecovering(true);
			characterDao.save(character);

			List<UserItem> userItems = userItemDao.findByConsumableIdAndUserId(id, userId);
			if (userItems.size() > 0) {
				userItemDao.delete(userItems.get(0));
				userItemDao.flush();
			}
		}

		return new CharacterDto(character);
	}

	public void healCharacter() {
		List<Character> chars = characterDao.findByRecovering(true);

		for (Character c : chars) {
			if (c.getInCombat()) {
				break; // Assuming subsequent characters are also in combat; otherwise, consider
						// removing this.
			}

			// If character is at max HP and has no healing stacks left
			if (c.getHp() >= c.getMaxHp() || c.getHealingStack() <= 0) {
				c.setRecovering(false);
				c.setHealingPower(0);
				c.setHealingStack(0);
				continue; // Move to the next character
			}

			// Heal the character
			c.setHealingStack(c.getHealingStack() - 1);
			c.setHp(c.getHp() + c.getHealingPower());
		}
	}
	
	public void renameCharacter(Long id, String name, Long userId) {
		
		if (name == null || name.trim().isEmpty()) {
			throw new AppException("The field is empty", HttpStatus.FORBIDDEN);
		}
		
		if (name.length() > 12) {
			throw new AppException("The name cannot contain more than 12 letters", HttpStatus.FORBIDDEN);
		}
		
		User user = userDao.findById(userId)
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		
		if (user.getDiamond() >= 50) {
			Character character = characterDao.findById(id)
					.orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));
			character.setName(name);
			characterDao.save(character);
			
			user.setDiamond(user.getDiamond() - 50);
			userDao.save(user);
		} else {
			throw new AppException("Not enough diamonds !", HttpStatus.FORBIDDEN);
		}

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
			boolean hasLeftOrZweiHand = currentChar.getEquippedItems().stream().anyMatch(
					item -> item.getSlot() == EquipmentSlot.LEFTHAND || item.getSlot() == EquipmentSlot.ZWEIHAND);
			if (!hasLeftOrZweiHand) {
				currentChar.setAtk(currentChar.getAtk());
			}
		}
	}

	private void handleArmorEquipping(Character currentChar, Equipment equipment) {
		Armor armor = armorDao.findByEquipmentId(equipment.getId())
				.orElseThrow(() -> new AppException("Unknown armor", HttpStatus.NOT_FOUND));
		EquipmentSlot otherPartSlot = equipment.getSlot() == EquipmentSlot.HEAD ? EquipmentSlot.BODY
				: EquipmentSlot.HEAD;

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
				.orElseThrow(() -> new AppException("Unknown armor", HttpStatus.NOT_FOUND)).getArmorValue();
	}

	private EquippedItem findExistingEquippedItemBySlot(Character character, EquipmentSlot slot) {
		return character.getEquippedItems().stream().filter(ei -> ei.getSlot() == slot).findFirst().orElse(null);
	}

}
