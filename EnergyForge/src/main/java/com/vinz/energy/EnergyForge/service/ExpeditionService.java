package com.vinz.energy.EnergyForge.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
				.orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));

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

	    beginFight(calculateTeamStats(yourTeam, enemyTeam));
	    return null;
	}


	private CumulatedTeamStats calculateTeamStats(List<Character> yourTeam, List<Character> enemyTeam) {
		CumulatedTeamStats allTeamStats = new CumulatedTeamStats();

		for (Character c : yourTeam) {
			CharacterDto dto = characterMapper.toDto(c);

			allTeamStats.setTotalHp(allTeamStats.getTotalHp() + dto.getHp());
			allTeamStats.setTotalArmor(allTeamStats.getTotalArmor() + dto.getArmor());
			allTeamStats.getYourDto().add(dto);
		}

		for (Character c : enemyTeam) {
			CharacterDto dto = characterMapper.toDto(c);

			allTeamStats.setEnemyHp(allTeamStats.getEnemyHp() + dto.getHp());
			allTeamStats.setEnemyArmor(allTeamStats.getEnemyArmor() + dto.getArmor());
			allTeamStats.getEnemyDto().add(dto);
		}

		return allTeamStats;
	}

	// TODO: nouveauté sur CombatSystem
	// Ajouter notion morale
	// DeathCount
	// Xp fin
	// si pas de weapon équppé on prend 8 variable de classe
	// Spread hp losses
	private void beginFight(CumulatedTeamStats allTeamStats) {
	    List<WeaponDto> yourWeapon = fetchTeamWeapons(allTeamStats.getYourDto());
	    List<WeaponDto> enemyWeapon = fetchTeamWeapons(allTeamStats.getEnemyDto());

		List<Effect> yourEffect = new ArrayList<Effect>();
		List<Effect> enemyEffect = new ArrayList<Effect>();
		for (WeaponDto w : yourWeapon) {
			yourEffect.add(w.getWeaponAbilities().get(0).getEquipmentAbilityEffect());
			if(!w.getIsMelee()) {
				yourEffect.add(Effect.RANGED_REDUCTION);
			}
		}
		for (WeaponDto w : enemyWeapon) {
			enemyEffect.add(w.getWeaponAbilities().get(0).getEquipmentAbilityEffect());
			if(!w.getIsMelee()) {
				enemyEffect.add(Effect.RANGED_REDUCTION);
			}
		}

		while (allTeamStats.getTotalHp() > 0 && allTeamStats.getEnemyHp() > 0) {

			// ENEMYWEAPON AGAINST YOUR TEAM
			for (WeaponDto w : enemyWeapon) {

				AttackActions aa = new AttackActions(Arrays.asList(8), 1);

				if (w.getId() != null) {
					aa = weaponSpecialization(w);
				}

				for (int i = 0; i < aa.getAtkCount(); i++) {
					Integer attackValue = aa.getAtkAmount().get(i);
					
					if(yourEffect.contains(Effect.RANGED_REDUCTION)) {
						attackValue = attackValue/2;
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

				}
				
								
				//Si backlash il ya (ROUNDSWING)
				allTeamStats.setTotalHp(allTeamStats.getTotalHp() - aa.getBacklash());

			}

			// YOURWEAPON AGAINST ENEMY TEAM
			for (WeaponDto w : yourWeapon) {

				AttackActions aa = new AttackActions(Arrays.asList(8), 1);

				if (w.getId() != null) {
					aa = weaponSpecialization(w);
				}

				for (int i = 0; i < aa.getAtkCount(); i++) {

					Integer attackValue = aa.getAtkAmount().get(i);
					
					if(yourEffect.contains(Effect.RANGED_REDUCTION)) {
						attackValue = attackValue/2;
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

				}

				//Si backlash il ya (ROUNDSWING)
				allTeamStats.setEnemyHp(allTeamStats.getEnemyHp() - aa.getBacklash());

			}

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
	        List<EquippedItemDto> equippedWeapons = c.getEquippedItems().stream()
	                .filter(e -> e.getSlot().isWeapon())
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
