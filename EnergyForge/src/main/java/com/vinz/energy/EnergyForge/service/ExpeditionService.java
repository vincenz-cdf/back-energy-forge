package com.vinz.energy.EnergyForge.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.CombatInstanceDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.CombatInstance;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.enums.CombatStatus;
import com.vinz.energy.EnergyForge.db.enums.Effect;
import com.vinz.energy.EnergyForge.db.enums.Scenario;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.dto.AttackActions;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.CombatIndexes;
import com.vinz.energy.EnergyForge.dto.CombatInstanceDto;
import com.vinz.energy.EnergyForge.dto.CumulatedTeamStats;
import com.vinz.energy.EnergyForge.dto.TriggeringEffect;
import com.vinz.energy.EnergyForge.dto.equipments.EquippedItemDto;
import com.vinz.energy.EnergyForge.dto.equipments.WeaponDto;
import com.vinz.energy.EnergyForge.dto.equipments.abilities.WeaponAbilityDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.CharacterMapper;
import com.vinz.energy.EnergyForge.mappers.WeaponMapper;

@Service
public class ExpeditionService {

	private static final Logger logger = LoggerFactory.getLogger(ExpeditionService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private CharacterDao characterDao;

	@Autowired
	private CharacterMapper characterMapper;

	@Autowired
	private CombatInstanceDao combatInstanceDao;

	@Autowired
	private WeaponMapper weaponMapper;

	@Autowired
	private WeaponDao weaponDao;

	private Random random = new Random();
	
	int SHIELDWALLVALUE = 15;
	int STAGGERVALUE = 33;
	int KOVALUE = 75;
	int SPEARWALLVALUE = 20;
	int SPEARBACKLASHCOUNT = 0;

	private Long yourUserId;
	private Long enemyUserId;

	public CombatInstanceDto createOrRetrieveCombatI(String scenarioEnum, Long userId) {

		try {

			Scenario scenario = Scenario.valueOf(scenarioEnum);
			User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
			boolean isCampaign = scenario.isCampaign();

			CombatInstance ci = combatInstanceDao.findByUserIdAndScenarioAndStatus(userId, scenario, CombatStatus.AWAIT)
					.orElse(null);

			if (ci == null) {
				if (isCampaign) {
					CombatInstance combatInstance = new CombatInstance();
					combatInstance.setScenario(scenario);
					combatInstance.setLabel(scenario.traductEnumToLabel());
					combatInstance.setStatus(CombatStatus.AWAIT);
					combatInstance.setUser(user);

					List<Character> chars = pregenerateNpcsForCampaign(scenario);

					combatInstance.setCharacters(chars);
					combatInstance.setIndexes(new ArrayList<CombatIndexes>());

					combatInstanceDao.save(combatInstance);

					return new CombatInstanceDto(combatInstance);
				} else {
					// ...opencontracts
					// combatInstance.setIndexes(calculateNpcIndexes(chars));
				}
			}

			return new CombatInstanceDto(ci);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	private List<Character> pregenerateNpcsForCampaign(Scenario scenario) {
		List<Character> characters = new ArrayList<Character>();
		switch (scenario) {
		case T_L1:
			characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING1));
			break;
		default:
			break;

		}

		return characters;
	}

	private List<CombatIndexes> calculateNpcIndexes(List<Character> chars) {

		List<CombatIndexes> indexes = new ArrayList<CombatIndexes>();
		List<Equipment> leftHandEquipment = new ArrayList<>();
		List<Equipment> rightHandEquipment = new ArrayList<>();

		for (int i = 0; i < chars.size(); i++) {

			CombatIndexes ci = new CombatIndexes();
			ci.setCharacterIndex(i);

			for (EquippedItem ei : chars.get(i).getEquippedItems()) {
				if (ei.getEquipment().getSlot() == EquipmentSlot.LEFTHAND
						|| ei.getEquipment().getSlot() == EquipmentSlot.ZWEIHAND) {
					leftHandEquipment.add(ei.getEquipment());
				}
				if (ei.getEquipment().getSlot() == EquipmentSlot.RIGHTHAND) {
					rightHandEquipment.add(ei.getEquipment());
				}
			}

			int weaponIndex = random.nextInt(leftHandEquipment.size());
			ci.setWeaponIndex(weaponIndex);

			if (leftHandEquipment.get(weaponIndex).getSlot() != EquipmentSlot.ZWEIHAND) {
				ci.setShieldIndex(random.nextInt(leftHandEquipment.size()));
			} else {
				ci.setShieldIndex(null);
			}

			indexes.add(ci);
		}

		return indexes;
	}

	public List<CharacterDto> getCharactersForCombat(Long id) {

		CombatInstance combatInstance = combatInstanceDao.findById(id)
				.orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));

		return characterMapper.toCharacterDtos(combatInstance.getCharacters());
	}

	public List<CharacterDto> listCurrentCharacters(Long userId) {

		return characterMapper.toCharacterDtos(characterDao.findByUserId(userId));
	}

	public void putHeroCharacterInCombat(Long ciId, Long charId) {
		CombatInstance combatInstance = combatInstanceDao.findById(ciId)
				.orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));

		Character character = characterDao.findById(charId)
				.orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));

		if (combatInstance.getCharacters().contains(character)) {
			combatInstance.getCharacters().remove(character);
		} else {
			combatInstance.getCharacters().add(character);
		}

		combatInstanceDao.save(combatInstance);

	}

	public CombatInstanceDto calculateCombatResult(Long combatInstanceId) {
		CombatInstance combatInstance = combatInstanceDao.findById(combatInstanceId)
				.orElseThrow(() -> new AppException("Unknown char", HttpStatus.NOT_FOUND));

		List<Character> allCharacters = combatInstance.getCharacters();
		List<Character> yourTeam = new ArrayList<>();
		List<Character> enemyTeam = new ArrayList<>();

		for (Character character : allCharacters) {
			if (character.getCharacterType() == CharacterType.HERO) {
				yourTeam.add(character);
			} else {
				enemyTeam.add(character);
			}
		}

		if (yourTeam.isEmpty() || enemyTeam.isEmpty()) {
			throw new AppException("Not enough chars", HttpStatus.BAD_REQUEST);
		}

		int remainingHealth = beginFight(calculateTeamStats(yourTeam, enemyTeam));
		
		return null;
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

		//TODO: Rework pour helper ally
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

			for (TriggeringEffect e : yourEffect) {
				if (e.getEffect() == Effect.SHIELDWALL && enemyShieldSplitterCount > 0) {
					yourEffect.remove(e);
					enemyShieldSplitterCount -= 1;
				}
			}

			for (TriggeringEffect e : enemyEffect) {
				if (e.getEffect() == Effect.SHIELDWALL && yourShieldSplitterCount > 0) {
					enemyEffect.remove(e);
					yourShieldSplitterCount -= 1;
				}
			}

			int yourRangedBros = Collections.frequency(yourEffect, Effect.RANGED_REDUCTION);
			int enemyRangedBros = Collections.frequency(enemyEffect, Effect.RANGED_REDUCTION);

			adjustRangedEffects(enemyEffect, yourRangedBros);
			adjustRangedEffects(yourEffect, enemyRangedBros);

			int totalDmgByEnemy = processWeaponsAgainstTeam(enemyWeapon, allTeamStats.getYourStats(), yourEffect,
					yourRangedBros, yourDeathCount, enemyEffect, enemyUserId);
			int totalDmgByYou = processWeaponsAgainstTeam(yourWeapon, allTeamStats.getEnemyStats(), enemyEffect,
					enemyRangedBros, enemyDeathCount, yourEffect, yourUserId);

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
			List<TriggeringEffect> defenderEffects, int rangedBrosCount, int deathCount,
			List<TriggeringEffect> yourEffects, Long userId) {
		SPEARBACKLASHCOUNT = 0;
		for (TriggeringEffect e : yourEffects) {
			if (e.getEffect() == Effect.SPEARWALL_BACKLASH) {
				SPEARBACKLASHCOUNT++;
			}
		}
		int totalDamage = 0;
		for (WeaponDto weapon : attackingWeapons.stream()
				.filter(w -> w.getEquipment().getSlot() != EquipmentSlot.RIGHTHAND).collect(Collectors.toList())) {

			AttackActions aa = (weapon.getId() != null) ? weaponSpecialization(weapon, yourEffects)
					: new AttackActions(Arrays.asList(8), 1);

			int totalDamageDealtByWeapon = 0;

			for (TriggeringEffect e : defenderEffects) {

				// REPEL & STAGGER
				if (e.getEffect() == Effect.REPEL && random.nextInt(100) + 1 <= STAGGERVALUE
						&& rangedBrosCount >= attackingWeapons.stream().filter(
								w -> w.getEquipment().getSlot() != EquipmentSlot.RIGHTHAND && aa.getAtkCount() > 0)
								.collect(Collectors.toList()).size() - deathCount) {
					aa.setAtkCount(aa.getAtkCount() - 1);
					defenderEffects.remove(e);
				}

				// SHIELD WALL BLOCK 1 ATK
				if (e.getEffect() == Effect.SHIELDWALL && random.nextInt(100) + 1 <= SHIELDWALLVALUE
						&& aa.getAtkCount() > 0) {
					if (weapon.getWeaponAbilities().get(0).getEquipmentAbilityEffect() == Effect.LASH) {
						aa.getAtkAmount().set(0, (int) Math.round(weapon.getAtkValue() * 1.6));
						if (aa.getAtkCount() == 0) {
							aa.setAtkCount(1);
						}
					} else {
						aa.setAtkCount(aa.getAtkCount() - 1);
						defenderEffects.remove(e);
					}
				}
				
				// Mace Stun
				if (e.getEffect() == Effect.KO && aa.getAtkCount() > 0) {
					aa.setAtkCount(0);
					defenderEffects.remove(e);
				}

				// Spear Wall
				if (e.getEffect() == Effect.SPEARWALL && aa.getAtkCount() > 0 && weapon.getIsMelee()) {
					aa.setAtkCount(0);
					defenderEffects.remove(e);
				}

				// Ranged Reduction
				if (e.getEffect() == Effect.RANGED_REDUCTION && rangedBrosCount < attackingWeapons.stream()
						.filter(w -> w.getEquipment().getSlot() != EquipmentSlot.RIGHTHAND).collect(Collectors.toList())
						.size() - deathCount) {
					aa.getAtkAmount().set(0, aa.getAtkAmount().get(0) / 2);
					defenderEffects.remove(e);
				}
			}

			for (TriggeringEffect e : yourEffects) {
				// Reduction /2 pour mace qui a trigger le stun
				if (e.getEffect() == Effect.KO_BACKLASH && weapon.getCharacterId() == e.getCharacterId()
						&& weapon.getId() == e.getWeaponId()) {
					aa.getAtkAmount().set(0, aa.getAtkAmount().get(0) / 2);
					yourEffects.remove(e);
				}
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

		if (weapon.getWeaponAbilities().get(0).getEquipmentAbilityEffect() == Effect.DARMOR) {
			aa.getAtkAmount().set(0, aa.getAtkAmount().get(0) + (int) Math.round(defenderStats.getArmor() * 0.33));
		}

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
		Iterator<TriggeringEffect> iterator = effects.iterator();
		int removedEffectsCount = 0;

		while (iterator.hasNext() && removedEffectsCount < rangedBrosCount) {
			if (iterator.next().getEffect() == Effect.RANGED_REDUCTION) {
				iterator.remove();
				removedEffectsCount++;
			}
		}
	}

	private AttackActions weaponSpecialization(WeaponDto weapon, List<TriggeringEffect> yourEffects) {

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

			if (a.getEquipmentAbilityEffect() == Effect.CLEAVE) {
				aa.setDirectHpDamage(aa.getDirectHpDamage() + 6);
			}
		}

		for (TriggeringEffect e : yourEffects) {
			if (e.getEffect() == Effect.SPEARWALL_BACKLASH) {
				if (weapon.getIsMelee() && weapon.getId() != e.getWeaponId()
						&& e.getCharacterId() != weapon.getCharacterId()) {
					aa.setAtkCount(0);
				}
			}

			if (SPEARBACKLASHCOUNT > 0 && e.getWeaponId() == weapon.getId() && e.getCharacterId() == e.getCharacterId()
					&& weapon.getUserId() == e.getUserId()) {
				aa.setAtkCount(SPEARBACKLASHCOUNT);
				aa.getAtkAmount().set(0, aa.getAtkAmount().get(0) / 2);
				for (int i = 0; i < SPEARBACKLASHCOUNT - 1; i++) {
					aa.getAtkAmount().add(weapon.getAtkValue() / 2);
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
