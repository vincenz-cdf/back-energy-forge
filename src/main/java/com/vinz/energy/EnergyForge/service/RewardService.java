package com.vinz.energy.EnergyForge.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.ConsumableDao;
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.CombatInstance;
import com.vinz.energy.EnergyForge.db.enums.Scenario;
import com.vinz.energy.EnergyForge.dto.CombatReward;
import com.vinz.energy.EnergyForge.dto.ConsumableDto;
import com.vinz.energy.EnergyForge.dto.RewardConfig;
import com.vinz.energy.EnergyForge.dto.equipments.EquipmentDto;
import com.vinz.energy.EnergyForge.exception.AppException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RewardService {

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private ConsumableDao consumableDao;

	private Random random = new Random();

	private static final Map<Scenario, RewardConfig> REWARD_CONFIG_MAP;

	static {
		REWARD_CONFIG_MAP = new HashMap<>();
		REWARD_CONFIG_MAP.put(Scenario.T_L1, new RewardConfig(15, 80, Arrays.asList(7L, 4L), Arrays.asList(1, 1), 5));
		REWARD_CONFIG_MAP.put(Scenario.T_L2, new RewardConfig(30, 90, Arrays.asList(22L, 16L), Arrays.asList(1, 1), 5));
		REWARD_CONFIG_MAP.put(Scenario.T_L3, new RewardConfig(30, 100, Arrays.asList(14L, 24L), Arrays.asList(1, 1), 5));
		REWARD_CONFIG_MAP.put(Scenario.T_L4, new RewardConfig(30, 150, Collections.emptyList(), Arrays.asList(2, 2, 2), 5));
	}

	@Transactional
	public CombatReward calculateCombatRewards(CombatInstance combatInstance, List<Character> enemyTeam,
			boolean hasWon) {
		CombatReward reward = new CombatReward();

		if (combatInstance.getScenario().isCampaign()) {
			setCampaignRewards(combatInstance, reward);
		} else {
			setEnemyTeamRewards(enemyTeam, reward);
		}

		return reward;
	}

	private void setCampaignRewards(CombatInstance combatInstance, CombatReward reward) {
		RewardConfig config = getRewardConfigForScenario(combatInstance.getScenario());

		reward.setExperiencePoints(config.getExperiencePoints());
		reward.setCoins(config.getCoins());
		reward.setDiamonds(config.getDiamonds());

		List<EquipmentDto> equipments = config.getEquipmentIds().stream()
				.map(id -> new EquipmentDto(equipmentDao.findById(id)
						.orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND))))
				.collect(Collectors.toList());

		List<ConsumableDto> consumables = config.getConsumableIds().stream()
				.map(id -> new ConsumableDto(consumableDao.findById(id)
						.orElseThrow(() -> new AppException("Unknown consumable", HttpStatus.NOT_FOUND))))
				.collect(Collectors.toList());

		reward.setEquipments(equipments);
		reward.setConsumables(consumables);
	}

	private void setEnemyTeamRewards(List<Character> enemyTeam, CombatReward reward) {
		for (Character c : enemyTeam) {
			reward.setExperiencePoints(reward.getExperiencePoints() + c.getExperiencePoint());
			reward.setCoins(reward.getCoins() + (int) (random.nextInt(15) + 40 + c.getLevel() * random.nextDouble(2) + 1));
			
			if(random.nextInt(100) + 1 < 50) {
				reward.setDiamonds(reward.getDiamonds() + random.nextInt(3) + 1);
			}
			
		}
	}

	private RewardConfig getRewardConfigForScenario(Scenario scenario) {
		return REWARD_CONFIG_MAP.getOrDefault(scenario,
				new RewardConfig(30, 100, Collections.emptyList(), Collections.emptyList(), 0));
	}
}
