package com.vinz.energy.EnergyForge.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.ArmorDao;
import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.CombatInstanceDao;
import com.vinz.energy.EnergyForge.dao.ConsumableDao;
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.UserItemDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.CombatInstance;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.UserItem;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.enums.CombatStatus;
import com.vinz.energy.EnergyForge.db.enums.ItemType;
import com.vinz.energy.EnergyForge.db.enums.Scenario;
import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.db.equipments.Weapon;
import com.vinz.energy.EnergyForge.dto.ArmorLightDto;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.CombatIndexes;
import com.vinz.energy.EnergyForge.dto.CombatInstanceDto;
import com.vinz.energy.EnergyForge.dto.CombatReward;
import com.vinz.energy.EnergyForge.dto.ConsumableDto;
import com.vinz.energy.EnergyForge.dto.CumulatedTeamStats;
import com.vinz.energy.EnergyForge.dto.WeaponLightDto;
import com.vinz.energy.EnergyForge.dto.equipments.EquipmentDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.CharacterMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
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
	private CombatService combatService;

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private UserItemDao userItemDao;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private RewardService rewardService;

	@Autowired
	private ConsumableDao consumableDao;

	@Autowired
	private WeaponDao weaponDao;

	@Autowired
	private ArmorDao armorDao;

	private Random random = new Random();

	public CombatInstanceDto createOrRetrieveCombatI(String scenarioEnum, Long userId) {

		try {
			Scenario scenario = Scenario.valueOf(scenarioEnum);
			User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
			boolean isCampaign = scenario.isCampaign();

			CombatInstance ci = combatInstanceDao.findByUserIdAndScenarioAndStatusIn(userId, scenario,
					Arrays.asList(CombatStatus.AWAIT, CombatStatus.WON_AWAIT, CombatStatus.LOST_AWAIT,
							CombatStatus.WON_FINAL, CombatStatus.LOST_FINAL))
					.orElse(null);

			if (ci == null) {
				if (isCampaign) {
					CombatInstance combatInstance = new CombatInstance();
					combatInstance.setScenario(scenario);
					combatInstance.setLabel(scenario.traductEnumToLabel());
					combatInstance.setStatus(CombatStatus.AWAIT);
					combatInstance.setUser(user);

					List<Character> chars = pregenerateNpcsForCampaign(scenario, user);

					combatInstance.setCharacters(chars);
					combatInstance.setIndexes(new ArrayList<CombatIndexes>());

					combatInstanceDao.save(combatInstance);

					return new CombatInstanceDto(combatInstance);
				}
			}

			return new CombatInstanceDto(ci);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	public void createContractCombatInstance(Long userId) {

		User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		CombatInstance combatInstance = new CombatInstance();
		combatInstance.setScenario(Scenario.CONTRACT);
		combatInstance.setLabel("Open Contract");
		combatInstance.setStatus(CombatStatus.AWAIT);
		combatInstance.setUser(user);

		List<Character> chars = pregenerateNpcsForCampaign(Scenario.CONTRACT, user);

		combatInstance.setCharacters(chars);
		combatInstance.setIndexes(calculateNpcIndexes(chars));

		combatInstanceDao.save(combatInstance);
	}

	private List<Character> pregenerateNpcsForCampaign(Scenario scenario, User user) {
		List<Character> characters = new ArrayList<Character>();

		if (scenario.isCampaign()) {
			switch (scenario) {
			case T_L1:
				characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING1));
				break;
			case T_L2:
				characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING2));
				characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING2));
				break;
			case T_L3:
				characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING3));
				characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING3));
				break;
			case T_L4:
				characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING4));
				break;
			default:
				break;

			}
		} else {
			int targetLevel = calculateTotalLevel(characterDao.findByUserId(user.getId()));

			List<Character> npcsAtTargetLevel = characterDao.findAll().stream() // Convert the list to a Stream
					.filter(character -> character.getCharacterType().isNpc() && character.getLevel() <= targetLevel)
					.collect(Collectors.toList());

			boolean isLevelConvenable = false;
			int totalEnemyLevel = 0;
			while (!isLevelConvenable) {

				characters.add(npcsAtTargetLevel.get(random.nextInt(npcsAtTargetLevel.size())));
				totalEnemyLevel = calculateTotalLevel(characters);
				if (totalEnemyLevel > targetLevel) {
					characters = new ArrayList<Character>();
					totalEnemyLevel = 0;
				} else {
					if (totalEnemyLevel == targetLevel) {
						isLevelConvenable = true;
					}
				}
			}
		}

		return characters;
	}

	private int calculateTotalLevel(List<Character> characters) {
		int totalLevel = 0;
		for (Character c : characters) {
			totalLevel += c.getLevel();
		}
		return totalLevel;
	}

	private List<CombatIndexes> calculateNpcIndexes(List<Character> chars) {
		if (chars == null) {
			return Collections.emptyList(); // Handle null chars list
		}

		List<CombatIndexes> indexes = new ArrayList<>();

		for (int i = 0; i < chars.size(); i++) {
			Character currentChar = chars.get(i);
			if (currentChar == null) {
				continue; // Skip if currentChar is null
			}

			CombatIndexes ci = new CombatIndexes();
			ci.setCharacterId(currentChar.getId()); // Moved down after null check
			ci.setCharacterIndex(i);

			if (currentChar.getEquippedItems() == null) {
				continue; // Skip if getEquippedItems() is null
			}

			// Initialize equipment lists
			List<Equipment> leftHandEquipment = new ArrayList<>();
			List<Equipment> rightHandEquipment = new ArrayList<>();
			List<Equipment> bodyArmors = new ArrayList<>();
			List<Equipment> headArmors = new ArrayList<>();

			// Iterate over equipped items and categorize them
			for (EquippedItem ei : currentChar.getEquippedItems()) {
				if (ei != null && ei.getEquipment() != null && ei.getEquipment().getSlot() != null) {
					switch (ei.getEquipment().getSlot()) {
					case LEFTHAND:
					case ZWEIHAND:
						leftHandEquipment.add(ei.getEquipment());
						break;
					case RIGHTHAND:
						rightHandEquipment.add(ei.getEquipment());
						break;
					case BODY:
						bodyArmors.add(ei.getEquipment());
						break;
					case HEAD:
						headArmors.add(ei.getEquipment());
						break;
					}
				}
			}

			if (!leftHandEquipment.isEmpty()) {
				int weaponIndex = random.nextInt(leftHandEquipment.size());
				Weapon weapon = weaponDao.findByEquipmentId(leftHandEquipment.get(weaponIndex).getId())
						.orElseThrow(() -> new AppException("Unknown weapon", HttpStatus.NOT_FOUND));
				ci.setWeapon(new WeaponLightDto(weapon));

				if (!rightHandEquipment.isEmpty()) {
					int shieldIndex = random.nextInt(rightHandEquipment.size());
					Weapon shield = weaponDao.findByEquipmentId(rightHandEquipment.get(shieldIndex).getId())
							.orElseThrow(() -> new AppException("Unknown shield", HttpStatus.NOT_FOUND));

					Equipment equipment = leftHandEquipment.get(weaponIndex);
					if (equipment != null && equipment.getSlot() != EquipmentSlot.ZWEIHAND) {
						if (currentChar.getCharacterType() != null
								&& random.nextInt(100) + 1 <= currentChar.getCharacterType().shieldPercent()) {
							ci.setShield(new WeaponLightDto(shield));
						}
					}
				}

			}

			if (!bodyArmors.isEmpty()) {
				int bodyIndex = random.nextInt(bodyArmors.size());
				Armor bodyArmor = armorDao.findByEquipmentId(bodyArmors.get(bodyIndex).getId())
						.orElseThrow(() -> new AppException("Unknown body", HttpStatus.NOT_FOUND));
				ci.setBodyArmor(new ArmorLightDto(bodyArmor));
			}

			if (!headArmors.isEmpty()) {
				int headArmorIndex = random.nextInt(headArmors.size());
				Armor headArmor = armorDao.findByEquipmentId(headArmors.get(headArmorIndex).getId())
						.orElseThrow(() -> new AppException("Unknown head", HttpStatus.NOT_FOUND));

				if (currentChar.getCharacterType() != null
						&& random.nextInt(100) + 1 <= currentChar.getCharacterType().headPercent()) {
					ci.setHeadArmor(new ArmorLightDto(headArmor));
				}
			}

			indexes.add(ci);
		}

		return indexes;
	}

	public CombatInstanceDto getCombatInstance(Long combatInstanceId) {
		CombatInstance combatInstance = combatInstanceDao.findById(combatInstanceId)
				.orElseThrow(() -> new RuntimeException("Combat Instance not found"));

		return new CombatInstanceDto(combatInstance);
	}

	public List<CharacterDto> getCharactersForCombat(Long id) {

		CombatInstance combatInstance = combatInstanceDao.findById(id)
				.orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));

		return characterMapper.toCharacterDtos(combatInstance.getCharacters());
	}

	public List<CombatInstanceDto> getCombatInstanceForUser(Long userId, String content) {
		List<CombatInstance> combatInstances = new ArrayList<CombatInstance>();

		if (content.equals("campaign")) {
			combatInstances = combatInstanceDao.findByUserIdAndScenarioNotOrderByIdAsc(userId, Scenario.CONTRACT);
		} else {
			combatInstances = combatInstanceDao.findByUserIdAndScenarioOrderByIdAsc(userId, Scenario.CONTRACT);
		}

		List<CombatInstanceDto> dtos = new ArrayList<CombatInstanceDto>();
		for (CombatInstance c : combatInstances) {
			dtos.add(new CombatInstanceDto(c));
		}

		return dtos;
	}

	public List<CharacterDto> listCurrentCharacters(Long userId, boolean fromHabit) {

		if (fromHabit) {
			return characterMapper.toCharacterDtos(characterDao.findByUserId(userId));
		}

		return characterMapper.toCharacterDtos(characterDao.findCharactersNotInCombatForUser(userId));
	}

	public void putHeroCharacterInCombat(Long ciId, Long charId) {
		CombatInstance combatInstance = combatInstanceDao.findById(ciId)
				.orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));

		Character character = characterDao.findById(charId)
				.orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));

		if (combatInstance.getCharacters().contains(character)) {
			character.setAwaiting(false);
			combatInstance.getCharacters().remove(character);
		} else {
			character.setAwaiting(true);
			combatInstance.getCharacters().add(character);
		}

		characterDao.save(character);
		combatInstanceDao.save(combatInstance);

	}

	@Transactional
	public void calculateCombatResult(Long combatInstanceId, Long userId) {
		try {
			CombatInstance combatInstance = combatInstanceDao.findById(combatInstanceId)
					.orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));

			List<Character> allCharacters = combatInstance.getCharacters();
			List<Character> yourTeam = allCharacters.stream().filter(c -> c.getCharacterType() == CharacterType.HERO)
					.collect(Collectors.toList());
			List<Character> enemyTeam = allCharacters.stream().filter(c -> c.getCharacterType() != CharacterType.HERO)
					.collect(Collectors.toList());

			profileService.manageEnergyCost(userId, yourTeam.size() * 2);

			if (yourTeam.isEmpty() || enemyTeam.isEmpty()) {
				throw new AppException("Not enough chars", HttpStatus.BAD_REQUEST);
			}

			CumulatedTeamStats finalResult = combatService
					.beginFight(combatService.calculateTeamStats(yourTeam, enemyTeam, combatInstance), combatInstance);
			boolean hasWon = finalResult.getYourStats().getHp() > finalResult.getEnemyStats().getHp();
			combatInstance.setStatus(hasWon ? CombatStatus.WON_AWAIT : CombatStatus.LOST_AWAIT);
			combatInstance.setAfterMathInfo(finalResult);

			CombatReward reward = rewardService.calculateCombatRewards(combatInstance, enemyTeam, hasWon);

			if (hasWon) {
				combatInstance.setCombatReward(reward);
			}

			combatInstanceDao.save(combatInstance);

			for (Character c : yourTeam) {
				c.setAwaiting(false);
				c.setInCombat(true);
				characterDao.save(c);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	// TODO: nouveauté sur CombatSystem
	// Ajouter notion morale
	// Xp fin
	// Spread hp losses
	public void refreshCombatInstance() {

		List<CombatInstance> wonCombatInstances = combatInstanceDao.findByStatus(CombatStatus.WON_AWAIT);
		List<CombatInstance> lostCombatInstances = combatInstanceDao.findByStatus(CombatStatus.LOST_AWAIT);

		for (CombatInstance ci : wonCombatInstances) {
			ci.setStatus(CombatStatus.WON_FINAL);
			combatInstanceDao.save(ci);
		}
		for (CombatInstance ci : lostCombatInstances) {
			ci.setStatus(CombatStatus.LOST_FINAL);
			combatInstanceDao.save(ci);
		}

		combatInstanceDao.deleteAll(combatInstanceDao.findByStatusAndScenario(CombatStatus.AWAIT, Scenario.CONTRACT));

	}

	public void gatherCombatReward(Long combatInstanceId, Long userId) {

		try {
			CombatInstance ci = combatInstanceDao.findById(combatInstanceId)
					.orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));
			User user = userDao.findById(userId)
					.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

			CumulatedTeamStats finalResult = ci.getAfterMathInfo();
			List<Character> chars = ci.getCharacters();

			int yourHp = finalResult.getYourStats().getHp();
			int yourMorale = finalResult.getYourStats().getMorale();
			int heroCount = (int) chars.stream().filter(c -> c.getCharacterType() == CharacterType.HERO).count();

			int rewardExp = ci.getCombatReward().getExperiencePoints();
			int rewardCoins = ci.getCombatReward().getCoins();

			for (Character c : chars) {
				if (c.getCharacterType() == CharacterType.HERO) {
					if (yourHp > 0) {
						c.setHp(Math.max(1, yourHp / heroCount));
						c.setExperiencePoint(rewardExp / heroCount);
					} else {
						c.setHp(0);
						c.setExperiencePoint(rewardExp / heroCount / 2);
					}

					c.setMorale(yourMorale > 0 ? yourMorale / heroCount : 0);
					c.setMorale(c.getMorale() - 1);
					c.setInCombat(false);

					int toLevelUp = c.getLevel() * 1000;

					if (c.getExperiencePoint() >= toLevelUp) {
						c.setLevel(c.getLevel() + 1);
						c.setExperiencePoint(c.getExperiencePoint() - toLevelUp);
					}

					characterDao.save(c);
				}
			}

			user.setCoin(user.getCoin() + rewardCoins);

			for (EquipmentDto e : ci.getCombatReward().getEquipments()) {
				UserItem userItem = new UserItem();
				userItem.setUser(user);
				userItem.setEquipment(equipmentDao.findById(e.getId())
						.orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND)));
				userItem.setItemType(ItemType.EQUIPMENT);
				userItemDao.save(userItem);
			}

			for (ConsumableDto c : ci.getCombatReward().getConsumables()) {
				UserItem userItem = new UserItem();
				userItem.setUser(user);
				userItem.setConsumable(consumableDao.findById(c.getId())
						.orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND)));
				userItem.setItemType(ItemType.CONSUMABLE);
				userItemDao.save(userItem);
			}

			ci.setStatus(CombatStatus.CLOSED);
			combatInstanceDao.save(ci);

		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
		}
	}

}
