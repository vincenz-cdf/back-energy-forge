package com.vinz.energy.EnergyForge.service;

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
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.UserItemDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.CombatInstance;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.UserItem;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.enums.CombatStatus;
import com.vinz.energy.EnergyForge.db.enums.Scenario;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.CombatIndexes;
import com.vinz.energy.EnergyForge.dto.CombatInstanceDto;
import com.vinz.energy.EnergyForge.dto.CombatReward;
import com.vinz.energy.EnergyForge.dto.CumulatedTeamStats;
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

	public CombatInstanceDto getCombatInstance(Long combatInstanceId) {
		CombatInstance combatInstance = combatInstanceDao.findById(combatInstanceId)
				.orElseThrow(() -> new RuntimeException("Combat Instance not found"));

		return new CombatInstanceDto(combatInstance);
	}

	private List<Character> pregenerateNpcsForCampaign(Scenario scenario) {
		List<Character> characters = new ArrayList<Character>();
		switch (scenario) {
		case T_L1:
			characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING1));
			break;
		case T_L2:
			characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING2));
			characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING2));
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

	public List<CombatInstanceDto> getCombatInstanceForUser(Long userId) {
		List<CombatInstance> combatInstances = combatInstanceDao.findByUserIdOrderByIdAsc(userId);
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

		return characterMapper
				.toCharacterDtos(characterDao.findCharactersNotInCombatForUser(userId, CombatStatus.CLOSED));
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

	public void calculateCombatResult(Long combatInstanceId) {
		CombatInstance combatInstance = combatInstanceDao.findById(combatInstanceId)
				.orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));

		List<Character> allCharacters = combatInstance.getCharacters();
		List<Character> yourTeam = allCharacters.stream().filter(c -> c.getCharacterType() == CharacterType.HERO)
				.collect(Collectors.toList());
		List<Character> enemyTeam = allCharacters.stream().filter(c -> c.getCharacterType() != CharacterType.HERO)
				.collect(Collectors.toList());

		if (yourTeam.isEmpty() || enemyTeam.isEmpty()) {
			throw new AppException("Not enough chars", HttpStatus.BAD_REQUEST);
		}

		CumulatedTeamStats finalResult = combatService
				.beginFight(combatService.calculateTeamStats(yourTeam, enemyTeam));
		boolean hasWon = finalResult.getYourStats().getHp() > finalResult.getEnemyStats().getHp();
		combatInstance.setStatus(hasWon ? CombatStatus.WON_AWAIT : CombatStatus.LOST_AWAIT);
		combatInstance.setAfterMathInfo(finalResult);

		CombatReward reward = calculateCombatRewards(combatInstance, enemyTeam, hasWon);

		if (hasWon) {
			combatInstance.setCombatReward(reward);
		}

		combatInstanceDao.save(combatInstance);
	}

	private CombatReward calculateCombatRewards(CombatInstance combatInstance, List<Character> enemyTeam,
			boolean hasWon) {
		CombatReward reward = new CombatReward();

		List<EquipmentDto> equipments = new ArrayList<EquipmentDto>();

		if (combatInstance.getScenario().isCampaign()) {
			switch (combatInstance.getScenario()) {
			case T_L1:
				reward.setExperiencePoints(15);
				reward.setCoins(100);
				equipments.add(new EquipmentDto(equipmentDao.findById(7l)
						.orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND))));
				equipments.add(new EquipmentDto(equipmentDao.findById(4l)
						.orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND))));
				reward.setEquipments(equipments);
				break;
			case T_L2:
				reward.setExperiencePoints(30);
				reward.setCoins(100);
				break;
			default:
				break;
			}
		} else {
			for (Character c : enemyTeam) {
				reward.setExperiencePoints(reward.getExperiencePoints() + c.getExperiencePoint());
				reward.setCoins(
						reward.getCoins() + (int) (random.nextInt(10) + c.getLevel() * random.nextDouble(2) + 1));
			}
		}

		return reward;
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
		}
		for (CombatInstance ci : lostCombatInstances) {
			ci.setStatus(CombatStatus.LOST_FINAL);
		}

	}

	public void gatherCombatReward(Long combatInstanceId, Long userId) {

		CombatInstance ci = combatInstanceDao.findById(combatInstanceId)
				.orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));
		User user = userDao.findById(userId).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

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
				characterDao.save(c);
			}
		}

		user.setCoin(user.getCoin() + rewardCoins);

		for (EquipmentDto e : ci.getCombatReward().getEquipments()) {
			UserItem userItem = new UserItem();
			userItem.setUser(user);
			userItem.setEquipment(equipmentDao.findById(e.getId())
					.orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND)));
			userItemDao.save(userItem);
		}

		ci.setStatus(CombatStatus.CLOSED);
		combatInstanceDao.save(ci);
	}

}
