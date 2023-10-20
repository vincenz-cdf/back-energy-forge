package com.vinz.energy.EnergyForge.combat;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.enums.Effect;
import com.vinz.energy.EnergyForge.dto.AttackActions;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.CumulatedTeamStats;
import com.vinz.energy.EnergyForge.dto.equipments.EquippedItemDto;
import com.vinz.energy.EnergyForge.dto.equipments.WeaponDto;
import com.vinz.energy.EnergyForge.dto.equipments.abilities.WeaponAbilityDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.CharacterMapper;
import com.vinz.energy.EnergyForge.mappers.WeaponMapper;
import com.vinz.energy.EnergyForge.service.EquipmentService;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
@SpringBootTest
class CombatSystemTests {

	private static final Logger logger = LoggerFactory.getLogger(CombatSystemTests.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private CharacterDao characterDao;

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private CharacterMapper characterMapper;

	@Autowired
	private WeaponDao weaponDao;

	@Autowired
	private WeaponMapper weaponMapper;

	Random random = new Random();

	@Transactional
	private void initializeAll() {
		User user = new User();
		user.setEmail("a");
		user.setEnergy(0);
		user.setLogin("a");
		user.setPassword("sdqqqqqdsq");
		userDao.save(user);

		User user2 = new User();
		user2.setEmail("b");
		user2.setEnergy(0);
		user2.setLogin("b");
		user2.setPassword("bbbbbqsf");
		userDao.save(user2);

		Character character = new Character();
		character.setArmor(0);
		character.setAtk(0);
		character.setHp(50);
		character.setMaxHp(50);
		character.setMaxArmor(0);
		character.setLevel(1);
		character.setCharacterType(CharacterType.HERO);
		character.setName("test");
		character.setSpritePath("assets/sprites/characters/mainchar.png");
		character.setUser(user);
		character.setExperiencePoint(0);
		character.setMorale(0);
		character.setMaxMorale(10);
		characterDao.save(character);

		Character character2 = new Character();
		character2.setArmor(0);
		character2.setAtk(0);
		character2.setHp(50);
		character2.setMaxHp(50);
		character2.setMaxArmor(0);
		character2.setLevel(1);
		character2.setCharacterType(CharacterType.HERO);
		character2.setName("test2");
		character2.setSpritePath("assets/sprites/characters/mainchar.png");
		character2.setUser(user2);
		character2.setExperiencePoint(0);
		character2.setMorale(0);
		character2.setMaxMorale(10);
		characterDao.save(character2);

	}

	@Sql("/test-data.sql")
	@Test
	@Transactional
	void newTest() {

		try {
			initializeAll();

			equipmentService.equipCharacterWithSpecificGear(1l, 1l);
			equipmentService.equipCharacterWithSpecificGear(1l, 4l);

			equipmentService.equipCharacterWithSpecificGear(2l, 2l);
			equipmentService.equipCharacterWithSpecificGear(2l, 3l);

			beginFight(calculateTeamStats(Arrays.asList(characterDao.findById(1l).orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND))), 
					Arrays.asList(characterDao.findById(2l).orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND)))));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	private CumulatedTeamStats calculateTeamStats(List<Character> yourTeam, List<Character> enemyTeam) {
		CumulatedTeamStats allTeamStats = new CumulatedTeamStats();

		int i = 0;
		for (Character c : yourTeam) {
			CharacterDto dto = characterMapper.toDto(c);

			allTeamStats.setTotalHp(allTeamStats.getTotalHp() + dto.getHp());
			allTeamStats.setTotalArmor(allTeamStats.getTotalArmor() + dto.getArmor());
			allTeamStats.getYourDto().add(dto);

			if (i == 0) {
				allTeamStats.getYourDeathThreshold().add(dto.getHp() + dto.getArmor());
			} else {
				allTeamStats.getYourDeathThreshold()
						.add(allTeamStats.getEnemyDeathThreshold().get(i - 1) + dto.getHp() + dto.getArmor());
			}
			i++;
		}

		i = 0;
		for (Character c : enemyTeam) {
			CharacterDto dto = characterMapper.toDto(c);

			allTeamStats.setEnemyHp(allTeamStats.getEnemyHp() + dto.getHp());
			allTeamStats.setEnemyArmor(allTeamStats.getEnemyArmor() + dto.getArmor());
			allTeamStats.getEnemyDto().add(dto);

			if (i == 0) {
				allTeamStats.getEnemyDeathThreshold().add(dto.getHp() + dto.getArmor());
			} else {
				allTeamStats.getEnemyDeathThreshold()
						.add(allTeamStats.getEnemyDeathThreshold().get(i - 1) + dto.getHp() + dto.getArmor());
			}
			i++;
		}

		return allTeamStats;
	}

	// TODO: nouveauté sur CombatSystem
	// Ajouter notion morale
	// DeathCount
	// Xp fin
	// Spread hp losses
	private void beginFight(CumulatedTeamStats allTeamStats) {
		List<WeaponDto> yourWeapon = fetchTeamWeapons(allTeamStats.getYourDto());
		List<WeaponDto> enemyWeapon = fetchTeamWeapons(allTeamStats.getEnemyDto());

		Integer yourTotalPull = allTeamStats.getYourDeathThreshold()
				.get(allTeamStats.getYourDeathThreshold().size() - 1);
		Integer enemyTotalPull = allTeamStats.getEnemyDeathThreshold()
				.get(allTeamStats.getEnemyDeathThreshold().size() - 1);
		Integer yourDeathCount = 0;
		Integer enemyDeathCount = 0;

		while (allTeamStats.getTotalHp() > 0 && allTeamStats.getEnemyHp() > 0) {

			List<Effect> yourEffect = new ArrayList<Effect>();
			List<Effect> enemyEffect = new ArrayList<Effect>();
			for (WeaponDto w : yourWeapon) {
				yourEffect.add(w.getWeaponAbilities().get(0).getEquipmentAbilityEffect());
				if (!w.getIsMelee()) {
					yourEffect.add(Effect.RANGED_REDUCTION);
				}
			}
			for (WeaponDto w : enemyWeapon) {
				enemyEffect.add(w.getWeaponAbilities().get(0).getEquipmentAbilityEffect());
				if (!w.getIsMelee()) {
					enemyEffect.add(Effect.RANGED_REDUCTION);
				}
			}

			int yourRangedBros = Collections.frequency(yourEffect, Effect.RANGED_REDUCTION);
			int enemyRangedBros = Collections.frequency(enemyEffect, Effect.RANGED_REDUCTION);
			
			for(int i=0; i < yourRangedBros; i++) {
				enemyEffect.remove(Effect.RANGED_REDUCTION);
			}
			for(int i=0; i < enemyRangedBros; i++) {
				yourEffect.remove(Effect.RANGED_REDUCTION);
			}

			// ENEMYWEAPONS AGAINST YOUR TEAM
			for (WeaponDto w : enemyWeapon) {

				AttackActions aa = new AttackActions(Arrays.asList(8), 1);

				if (w.getId() != null) {
					aa = weaponSpecialization(w);
				}

				Integer totalDamageDealtByWeapon = 0;
				for (int i = 0; i < aa.getAtkCount(); i++) {
					Integer attackValue = aa.getAtkAmount().get(i);

					if (yourEffect.contains(Effect.RANGED_REDUCTION)
							&& yourRangedBros >= yourWeapon.size() - yourDeathCount) {
						attackValue = attackValue / 2;
						yourEffect.remove(Effect.RANGED_REDUCTION);
					}

					if (allTeamStats.getTotalArmor() > 0) {
						Integer newArmorValue = allTeamStats.getTotalArmor() - attackValue;

						if (newArmorValue <= 0) {
							allTeamStats.setTotalArmor(0);
							allTeamStats.setTotalHp(allTeamStats.getTotalHp() + newArmorValue);
						} else {
							allTeamStats.setTotalArmor(newArmorValue);
						}
					} else {
						allTeamStats.setTotalHp(allTeamStats.getTotalHp() - attackValue);
					}

					totalDamageDealtByWeapon += attackValue;
				}

				// Si backlash il ya (ROUNDSWING)
				allTeamStats.setTotalHp(allTeamStats.getTotalHp() - aa.getBacklash());

				// Compter les pseudo-morts
				yourTotalPull = yourTotalPull - totalDamageDealtByWeapon;
				int indexThreshold = allTeamStats.getYourDeathThreshold().size() - 1 - yourDeathCount;

				if (indexThreshold >= 0) {
					if (yourTotalPull < allTeamStats.getYourDeathThreshold().get(indexThreshold)) {
						yourDeathCount += 1;
					}
				}

				totalDamageDealtByWeapon = 0;

			}

			// YOURWEAPONS AGAINST ENEMY TEAM
			for (WeaponDto w : yourWeapon) {

				AttackActions aa = new AttackActions(Arrays.asList(8), 1);

				if (w.getId() != null) {
					aa = weaponSpecialization(w);
				}

				Integer totalDamageDealtByWeapon = 0;
				for (int i = 0; i < aa.getAtkCount(); i++) {

					Integer attackValue = aa.getAtkAmount().get(i);

					if (yourEffect.contains(Effect.RANGED_REDUCTION)
							&& enemyRangedBros >= enemyWeapon.size() - enemyDeathCount) {
						attackValue = attackValue / 2;
						yourEffect.remove(Effect.RANGED_REDUCTION);
					}

					if (allTeamStats.getEnemyArmor() > 0) {
						Integer newArmorValue = allTeamStats.getEnemyArmor() - attackValue;
						if (newArmorValue <= 0) {
							allTeamStats.setEnemyArmor(0);
							allTeamStats.setEnemyHp(allTeamStats.getEnemyHp() + newArmorValue);
						} else {
							allTeamStats.setEnemyArmor(newArmorValue);
						}
					} else {
						allTeamStats.setEnemyHp(allTeamStats.getEnemyHp() - attackValue);
					}
					totalDamageDealtByWeapon += attackValue;
				}

				// Si backlash il ya (ROUNDSWING)
				allTeamStats.setEnemyHp(allTeamStats.getEnemyHp() - aa.getBacklash());

				// Compter les pseudo-morts
				enemyTotalPull = enemyTotalPull - totalDamageDealtByWeapon;
				int indexThreshold = allTeamStats.getEnemyDeathThreshold().size() - 1 - enemyDeathCount;

				if (indexThreshold >= 0) {
					if (enemyTotalPull < allTeamStats.getEnemyDeathThreshold().get(indexThreshold)) {
						enemyDeathCount += 1;
					}
				}

			}

		}
		
		System.out.println(allTeamStats.getTotalHp());
		System.out.println(allTeamStats.getEnemyHp());

	}

	private AttackActions weaponSpecialization(WeaponDto weapon) {

		AttackActions aa = new AttackActions();
		aa.getAtkAmount().add(weapon.getAtkValue());

		for (WeaponAbilityDto a : weapon.getWeaponAbilities()) {

			if (a.getEquipmentAbilityEffect() == Effect.SWINGSPLIT) {
				if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue()) {
					aa.setAtkCount(aa.getAtkCount() + 1);
					if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue()) {
						aa.getAtkAmount().add(weapon.getAtkValue() * 2);
					} else {
						return aa;
					}
				}
			}

			if (a.getEquipmentAbilityEffect() == Effect.RIPOSTE) {
				if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue()) {
					aa.setAtkCount(aa.getAtkCount() + 1);
					aa.getAtkAmount().add(weapon.getAtkValue());
				}
			}

			if (a.getEquipmentAbilityEffect() == Effect.ROUNDSWING) {
				boolean firstStrike = false;

				if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue()) {
					aa.setAtkCount(aa.getAtkCount() + 1);
					aa.getAtkAmount().add(weapon.getAtkValue());
					firstStrike = true;
				}
				if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue() - 10) {
					aa.setBacklash(aa.getBacklash() + weapon.getAtkValue());
					firstStrike = true;
				}

				if (firstStrike) {
					if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue()) {
						aa.setAtkCount(aa.getAtkCount() + 1);
						aa.getAtkAmount().add(weapon.getAtkValue());
					}
					if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue() - 10) {
						aa.setBacklash(aa.getBacklash() + weapon.getAtkValue());
					}
				}

			}

		}

		return aa;

	}

	private List<WeaponDto> fetchTeamWeapons(List<CharacterDto> teamDto) {
		List<WeaponDto> teamWeapons = new ArrayList<>();

		for (CharacterDto c : teamDto) {
			List<EquippedItemDto> equippedWeapons = c.getEquippedItems().stream().filter(e -> e.getSlot().isWeapon())
					.collect(Collectors.toList());

			if (equippedWeapons.isEmpty()) {
				teamWeapons.add(new WeaponDto());
				continue;
			}

			for (EquippedItemDto weaponItem : equippedWeapons) {
				teamWeapons.add(weaponMapper.toDto(weaponDao.findByEquipmentId(weaponItem.getEquipmentId())
						.orElseThrow(() -> new AppException("Unknown char", HttpStatus.NOT_FOUND))));
			}
		}
		return teamWeapons;
	}
	
}
