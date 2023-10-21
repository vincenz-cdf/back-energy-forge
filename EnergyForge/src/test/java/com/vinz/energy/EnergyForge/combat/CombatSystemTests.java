package com.vinz.energy.EnergyForge.combat;

import static org.mockito.ArgumentMatchers.anyBoolean;

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
import com.vinz.energy.EnergyForge.dto.TriggeringEffect;
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
	int STAGGERVALUE = 33;
	int KOVALUE = 75;
	int SPEARWALLVALUE = 20;

	private Long yourUserId;
	private Long enemyUserId;

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

		int i = 0;

		for (Character c : yourTeam) {
			CharacterDto dto = characterMapper.toDto(c);
			yourStats.setHp(yourStats.getHp() + dto.getHp());
			yourStats.setArmor(yourStats.getArmor() + dto.getArmor());
			yourStats.getDto().add(dto);
			int totalForCurrentCharacter = dto.getHp() + dto.getArmor();
			if (i != yourTeam.size() - 1) {
				yourStats.getDeathThreshold().add(totalForCurrentCharacter);
			}
			i++;
		}

		i = 0;
		for (Character c : enemyTeam) {
			CharacterDto dto = characterMapper.toDto(c);
			enemyStats.setHp(enemyStats.getHp() + dto.getHp());
			enemyStats.setArmor(enemyStats.getArmor() + dto.getArmor());
			enemyStats.getDto().add(dto);
			int totalForCurrentCharacter = dto.getHp() + dto.getArmor();

			if (i != enemyTeam.size() - 1) {
				enemyStats.getDeathThreshold().add(totalForCurrentCharacter);
			}
			i++;
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

		// Rework pour helper ally
		this.yourUserId = yourWeapon.get(0).getUserId();
		this.enemyUserId = enemyWeapon.get(0).getUserId();

		while (allTeamStats.getYourStats().getHp() > 0 && allTeamStats.getEnemyStats().getHp() > 0) {

			List<TriggeringEffect> yourEffect = buildEffectsList(yourWeapon, enemyWeapon);
			List<TriggeringEffect> enemyEffect = buildEffectsList(enemyWeapon, yourWeapon);

			// Shield Splitter (Counter the shield count)
			long yourShieldSplitterCount = yourWeapon.stream().filter(weapon -> weapon.getWeaponAbilities().stream()
					.anyMatch(ability -> ability.getEquipmentAbilityEffect() == Effect.SHIELDSPLIT)).count();
			long enemyShieldSplitterCount = enemyWeapon.stream().filter(weapon -> weapon.getWeaponAbilities().stream()
					.anyMatch(ability -> ability.getEquipmentAbilityEffect() == Effect.SHIELDSPLIT)).count();

			// TODO: retirer nb SHIELDWALL

			int yourRangedBros = Collections.frequency(yourEffect, Effect.RANGED_REDUCTION);
			int enemyRangedBros = Collections.frequency(enemyEffect, Effect.RANGED_REDUCTION);

			adjustRangedEffects(enemyEffect, yourRangedBros);
			adjustRangedEffects(yourEffect, enemyRangedBros);

			int totalDmgByEnemy = processWeaponsAgainstTeam(enemyWeapon, allTeamStats.getYourStats(), yourEffect,
					yourRangedBros, yourDeathCount, enemyEffect, enemyUserId, false);
			int totalDmgByYou = processWeaponsAgainstTeam(yourWeapon, allTeamStats.getEnemyStats(), enemyEffect,
					enemyRangedBros, enemyDeathCount, yourEffect, yourUserId, true);

			for (int i = 0 + enemyDeathCount; i < allTeamStats.getEnemyDeathThreshold().size(); i++) {
				if (totalDmgByYou > allTeamStats.getEnemyDeathThreshold().get(i)) {
					enemyDeathCount++;
					break;
				}
			}

			for (int i = 0 + yourDeathCount; i < allTeamStats.getYourDeathThreshold().size(); i++) {
				if (totalDmgByEnemy > allTeamStats.getYourDeathThreshold().get(i)) {
					yourDeathCount++;
					break;
				}
			}

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

	private int processWeaponsAgainstTeam(List<WeaponDto> attackingWeapons, CumulatedTeamStats.TeamStats defenderStats,
			List<TriggeringEffect> defenderEffects, int rangedBrosCount, int deathCount, List<TriggeringEffect> yourEffects, Long userId,
			boolean yourTurn) {
		int totalDamage = 0;
		for (WeaponDto weapon : attackingWeapons.stream()
				.filter(w -> w.getEquipment().getSlot() != EquipmentSlot.RIGHTHAND).collect(Collectors.toList())) {

			AttackActions aa = (weapon.getId() != null) ? weaponSpecialization(weapon)
					: new AttackActions(Arrays.asList(8), 1);

			int totalDamageDealtByWeapon = 0;

			Long userTurn = yourTurn ? this.yourUserId : this.enemyUserId;

			// SHIELD WALL BLOCK 1 ATK
			if (defenderEffects.contains(Effect.SHIELDWALL) && random.nextInt(100) + 1 <= SHIELDWALLVALUE
					&& aa.getAtkCount() > 0) {
				aa.setAtkCount(aa.getAtkCount() - 1);
				defenderEffects.remove(Effect.SHIELDWALL);
			}

			// REPEL & STAGGER
			if (defenderEffects.contains(Effect.REPEL) && random.nextInt(100) + 1 <= STAGGERVALUE
					&& rangedBrosCount >= attackingWeapons.stream()
							.filter(w -> w.getEquipment().getSlot() != EquipmentSlot.RIGHTHAND && aa.getAtkCount() > 0)
							.collect(Collectors.toList()).size() - deathCount) {
				aa.setAtkCount(aa.getAtkCount() - 1);
				defenderEffects.remove(Effect.SHIELDWALL);
			}

			// Mace Stun
			if (defenderEffects.contains(Effect.KO) && aa.getAtkCount() > 0) {
				aa.setAtkCount(0);
				defenderEffects.remove(Effect.KO);
			}

			// Stun Spear (la lance attaquera nb fois stun)
			if (defenderEffects.contains(Effect.SPEARWALL) && aa.getAtkCount() > 0) {
				aa.setAtkCount(0);
				defenderEffects.remove(Effect.SPEARWALL);
			}

			// Ranged Reduction
			if (defenderEffects.contains(Effect.RANGED_REDUCTION) && rangedBrosCount < attackingWeapons.stream()
					.filter(w -> w.getEquipment().getSlot() != EquipmentSlot.RIGHTHAND).collect(Collectors.toList())
					.size() - deathCount) {
				aa.getAtkAmount().set(0, aa.getAtkAmount().get(0) / 2);
				defenderEffects.remove(Effect.RANGED_REDUCTION);
			}

			// Reperer la bonne arme du bon perso du bon tour, tester avec weapon userId //
			// Arevoir

			// Reduction /2 pour mace qui a trigger le stun
			if (yourEffects.contains(Effect.KO_BACKLASH)) {
				aa.getAtkAmount().set(0, aa.getAtkAmount().get(0) / 2);
				yourEffects.remove(Effect.KO_BACKLASH);
			}

			// Reduction /2 pour le spear qui a envoyé l'attaque,
			if (yourEffects.contains(Effect.SPEARWALL_BACKLASH)) {
				aa.getAtkAmount().set(0, aa.getAtkAmount().get(0) / 2);

			}
			
			totalDamageDealtByWeapon = computeWeaponDamage(aa, defenderStats, weapon, defenderEffects, rangedBrosCount,
					deathCount, attackingWeapons, yourEffects);

			System.out.println(aa);
			System.out.println(weapon);
			System.out.println(totalDamageDealtByWeapon);

			// If there is a backlash (ROUNDSWING)
			defenderStats.setHp(defenderStats.getHp() - aa.getBacklash());

			totalDamage = totalDamageDealtByWeapon;
		}
		return totalDamage;

	}

	private int computeWeaponDamage(AttackActions aa, CumulatedTeamStats.TeamStats defenderStats, WeaponDto weapon,
			List<TriggeringEffect> defenderEffects, int rangedBrosCount, int deathCount, List<WeaponDto> weapons,
			List<TriggeringEffect> yourEffects) {

		int totalDamage = 0;

		for (int i = 0; i < aa.getAtkCount(); i++) {
			int attackValue = aa.getAtkAmount().get(i);

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

			defenderStats.setHp(defenderStats.getHp() - aa.getDirectHpDamage());
			totalDamage += aa.getDirectHpDamage();
		}
		return totalDamage;
	}

	private List<TriggeringEffect> buildEffectsList(List<WeaponDto> weapons, List<WeaponDto> enemyWeapons) {
		List<TriggeringEffect> effects = new ArrayList<>();
		for (WeaponDto weapon : weapons) {

			TriggeringEffect effect = new TriggeringEffect();
			effect.setCharacterId(weapon.getCharacterId());
			effect.setUserId(weapon.getUserId());
			effect.setWeaponId(weapon.getId());

			if (!weapon.getIsMelee()) {
				effect.setEffect(Effect.RANGED_REDUCTION);
				effects.add(effect);
			}

			int shieldWallCount = Collections.frequency(weapon.getWeaponAbilities(), Effect.SHIELDWALL);
			if (shieldWallCount > 0) {
				effect.setEffect(Effect.SHIELDWALL);
				effects.add(effect);
			}

			int repelCount = Collections.frequency(weapon.getWeaponAbilities(), Effect.REPEL);
			if (repelCount > 0) {
				effect.setEffect(Effect.REPEL);
				effects.add(effect);
			}

			int koCount = Collections.frequency(weapon.getWeaponAbilities(), Effect.KO);
			if (koCount > 0 && random.nextInt(100) + 1 <= KOVALUE) {

				effect.setEffect(Effect.KO);
				effects.add(effect);
				effect.setEffect(Effect.KO_BACKLASH);
				effects.add(effect);
			}

			int spearWallCount = Collections.frequency(weapon.getWeaponAbilities(), Effect.SPEARWALL);
			if (spearWallCount > 0) {
				for (int j = 0; j < enemyWeapons.size(); j++) {
					if (random.nextInt(100) + 1 <= SPEARWALLVALUE) {
						effect.setEffect(Effect.SPEARWALL);
						effects.add(effect);
						effect.setEffect(Effect.SPEARWALL_BACKLASH);
						effects.add(effect);
					}
				}
			}

		}
		return effects;
	}

	private void adjustRangedEffects(List<TriggeringEffect> effects, int rangedBrosCount) {
		for (int i = 0; i < rangedBrosCount && effects.contains(Effect.RANGED_REDUCTION); i++) {
			effects.remove(Effect.RANGED_REDUCTION);
		}
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

			if (a.getEquipmentAbilityEffect() == Effect.PUNCTURE) {
				if (random.nextInt(100) + 1 <= a.getEquipmentAbilityId()) {
					aa.getAtkAmount().set(0, 0);
					aa.setDirectHpDamage(aa.getDirectHpDamage() + weapon.getAtkValue());
				}
			}

			/*
			 * if (a.getEquipmentAbilityEffect() == Effect.KO ||
			 * a.getEquipmentAbilityEffect() == Effect.SPEARWALL) {
			 * aa.setWeaponId(weapon.getId()); }
			 */

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
				WeaponDto weapon = weaponMapper.toDto(weaponDao.findByEquipmentId(weaponItem.getEquipmentId())
						.orElseThrow(() -> new AppException("Unknown char", HttpStatus.NOT_FOUND)));

				if (equippedWeapons.size() == 2 && weapon.getEquipment().getSlot() == EquipmentSlot.LEFTHAND) {
					weapon.setAtkValue(weapon.getAtkValue() * 3 / 4);
				}

				weapon.setCharacterId(c.getId());
				weapon.setUserId(c.getUserId());

				teamWeapons.add(weapon);
			}
		}
		return teamWeapons.stream().sorted((w1, w2) -> {
			if (w1.getAtkValue() == null && w2.getAtkValue() == null) {
				return 0; // both are null, so they are considered equal
			}
			if (w1.getAtkValue() == null) {
				return 1; // w1 comes after w2
			}
			if (w2.getAtkValue() == null) {
				return -1; // w1 comes before w2
			}
			return w1.getAtkValue().compareTo(w2.getAtkValue()); // compare the actual values
		}).collect(Collectors.toList());
	}

}
