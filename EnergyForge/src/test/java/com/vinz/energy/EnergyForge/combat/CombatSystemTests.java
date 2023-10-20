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
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.enums.Effect;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
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
	private EquipmentService equipmentService;

	@Autowired
	private CharacterMapper characterMapper;

	@Autowired
	private WeaponDao weaponDao;

	@Autowired
	private WeaponMapper weaponMapper;

	Random random = new Random();

	int SHIELDWALLVALUE = 15;

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
			equipmentService.equipCharacterWithSpecificGear(1l, 22l);

			equipmentService.equipCharacterWithSpecificGear(2l, 2l);
			equipmentService.equipCharacterWithSpecificGear(2l, 16l);

			int remainingHealth = beginFight(calculateTeamStats(
					Arrays.asList(characterDao.findById(1l)
							.orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND))),
					Arrays.asList(characterDao.findById(2l)
							.orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND)))));

			// Calculate health/chars.size

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private CumulatedTeamStats calculateTeamStats(List<Character> yourTeam, List<Character> enemyTeam) {
		CumulatedTeamStats allTeamStats = new CumulatedTeamStats();
		CumulatedTeamStats.TeamStats yourStats = new CumulatedTeamStats.TeamStats();
		CumulatedTeamStats.TeamStats enemyStats = new CumulatedTeamStats.TeamStats();

		for (Character c : yourTeam) {
			CharacterDto dto = characterMapper.toDto(c);
			yourStats.setHp(yourStats.getHp() + dto.getHp());
			yourStats.setArmor(yourStats.getArmor() + dto.getArmor());
			yourStats.getDto().add(dto);
			int totalForCurrentCharacter = dto.getHp() + dto.getArmor();
			if (!yourStats.getDeathThreshold().isEmpty()) {
				totalForCurrentCharacter += yourStats.getDeathThreshold().get(yourStats.getDeathThreshold().size() - 1);
			}
			yourStats.getDeathThreshold().add(totalForCurrentCharacter);
		}

		for (Character c : enemyTeam) {
			CharacterDto dto = characterMapper.toDto(c);
			enemyStats.setHp(enemyStats.getHp() + dto.getHp());
			enemyStats.setArmor(enemyStats.getArmor() + dto.getArmor());
			enemyStats.getDto().add(dto);
			int totalForCurrentCharacter = dto.getHp() + dto.getArmor();
			if (!enemyStats.getDeathThreshold().isEmpty()) {
				totalForCurrentCharacter += enemyStats.getDeathThreshold()
						.get(enemyStats.getDeathThreshold().size() - 1);
			}
			enemyStats.getDeathThreshold().add(totalForCurrentCharacter);
		}

		allTeamStats.setYourStats(yourStats);
		allTeamStats.setEnemyStats(enemyStats);

		return allTeamStats;
	}

	// TODO: nouveauté sur CombatSystem
	// Ajouter notion morale
	// DeathCount
	// Xp fin
	// Spread hp losses
	private int beginFight(CumulatedTeamStats allTeamStats) {
		List<WeaponDto> yourWeapon = fetchTeamWeapons(allTeamStats.getYourStats().getDto());
		List<WeaponDto> enemyWeapon = fetchTeamWeapons(allTeamStats.getEnemyStats().getDto());

		int yourDeathCount = 0;
		int enemyDeathCount = 0;

		while (allTeamStats.getYourStats().getHp() > 0 && allTeamStats.getEnemyStats().getHp() > 0) {

			List<Effect> yourEffect = buildEffectsList(yourWeapon);
			List<Effect> enemyEffect = buildEffectsList(enemyWeapon);

			int yourRangedBros = Collections.frequency(yourEffect, Effect.RANGED_REDUCTION);
			int enemyRangedBros = Collections.frequency(enemyEffect, Effect.RANGED_REDUCTION);

			adjustRangedEffects(enemyEffect, yourRangedBros);
			adjustRangedEffects(yourEffect, enemyRangedBros);

			processWeaponsAgainstTeam(enemyWeapon, allTeamStats.getYourStats(), yourEffect, enemyRangedBros,
					yourDeathCount);
			processWeaponsAgainstTeam(yourWeapon, allTeamStats.getEnemyStats(), enemyEffect, yourRangedBros,
					enemyDeathCount);


			System.out.println("Your deaths: " + yourDeathCount);
			System.out.println("Enemy deaths: " + enemyDeathCount);

			System.out.println("=========");
			System.out.println("enemy hp");
			System.out.println(allTeamStats.getEnemyStats().getHp());
			System.out.println("enemy ar");
			System.out.println(allTeamStats.getEnemyStats().getArmor());
			System.out.println("=========");
			System.out.println("your hp");
			System.out.println(allTeamStats.getYourStats().getHp());
			System.out.println("your ar");
			System.out.println(allTeamStats.getYourStats().getArmor());
			System.out.println("=========");
		}

		// TODO: adapt to PvP
		return allTeamStats.getYourStats().getHp();
	}

	private List<Effect> buildEffectsList(List<WeaponDto> weapons) {
		List<Effect> effects = new ArrayList<>();
		for (WeaponDto weapon : weapons) {
			effects.add(weapon.getWeaponAbilities().get(0).getEquipmentAbilityEffect());

			if (!weapon.getIsMelee()) {
				effects.add(Effect.RANGED_REDUCTION);
			}

			int shieldWallCount = Collections.frequency(weapon.getWeaponAbilities(), Effect.SHIELDWALL);
			for (int i = 0; i < shieldWallCount; i++) {
				effects.add(Effect.SHIELDWALL);
			}
		}
		return effects;
	}

	private void adjustRangedEffects(List<Effect> effects, int rangedBrosCount) {
		for (int i = 0; i < rangedBrosCount && effects.contains(Effect.RANGED_REDUCTION); i++) {
			effects.remove(Effect.RANGED_REDUCTION);
		}
	}

	private int processWeaponsAgainstTeam(List<WeaponDto> attackingWeapons, CumulatedTeamStats.TeamStats defenderStats,
			List<Effect> defenderEffects, int rangedBrosCount, int deathCount) {
		for (WeaponDto weapon : attackingWeapons.stream()
				.filter(w -> w.getEquipment().getSlot() != EquipmentSlot.RIGHTHAND).collect(Collectors.toList())) {
			AttackActions aa = (weapon.getId() != null) ? weaponSpecialization(weapon)
					: new AttackActions(Arrays.asList(8), 1);
			int totalDamageDealtByWeapon = 0;

			if (defenderEffects.contains(Effect.SHIELDWALL)) {
				if (random.nextInt(100) + 1 > SHIELDWALLVALUE) {
					totalDamageDealtByWeapon = computeWeaponDamage(aa, defenderStats, defenderEffects, rangedBrosCount,
							deathCount, attackingWeapons);
					defenderEffects.remove(Effect.SHIELDWALL);
				}
			} else {
				totalDamageDealtByWeapon = computeWeaponDamage(aa, defenderStats, defenderEffects, rangedBrosCount,
						deathCount, attackingWeapons);
			}

			System.out.println(weapon);
			System.out.println(totalDamageDealtByWeapon);

			// If there is a backlash (ROUNDSWING)
			defenderStats.setHp(defenderStats.getHp() - aa.getBacklash());
			
			return totalDamageDealtByWeapon;
		}
		
		return 0;
	}

	private int computeWeaponDamage(AttackActions aa, CumulatedTeamStats.TeamStats defenderStats,
			List<Effect> defenderEffects, int rangedBrosCount, int deathCount, List<WeaponDto> weapons) {
		int totalDamage = 0;
		for (int i = 0; i < aa.getAtkCount(); i++) {
			int attackValue = aa.getAtkAmount().get(i);

			if (defenderEffects.contains(Effect.RANGED_REDUCTION) && rangedBrosCount > weapons.stream()
					.filter(w -> w.getEquipment().getSlot() != EquipmentSlot.RIGHTHAND).collect(Collectors.toList())
					.size() - deathCount) {
				attackValue /= 2;
				defenderEffects.remove(Effect.RANGED_REDUCTION);
			}

			if (defenderStats.getArmor() > 0) {
				int newArmorValue = defenderStats.getArmor() - attackValue;
				if (newArmorValue <= 0) {
					defenderStats.setArmor(0);
					defenderStats.setHp(defenderStats.getHp() + newArmorValue);
				} else {
					defenderStats.setArmor(newArmorValue);
				}
			} else {
				defenderStats.setHp(defenderStats.getHp() - attackValue);
			}
			totalDamage += attackValue;
		}
		return totalDamage;
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

			if (a.getEquipmentAbilityEffect() == Effect.RIPOSTE || a.getEquipmentAbilityEffect() == Effect.QUICKSHOT) {
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
