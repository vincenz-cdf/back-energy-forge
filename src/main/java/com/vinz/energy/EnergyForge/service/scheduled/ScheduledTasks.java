package com.vinz.energy.EnergyForge.service.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vinz.energy.EnergyForge.service.BuildingService;
import com.vinz.energy.EnergyForge.service.CharacterService;
import com.vinz.energy.EnergyForge.service.ExpeditionService;
import com.vinz.energy.EnergyForge.service.HabitService;

@Component
public class ScheduledTasks {

	@Autowired
	private CharacterService characterService;

	@Autowired
	private HabitService habitService;

	@Autowired
	private ExpeditionService expeditionService;

	@Autowired
	private BuildingService buildingService;

	@Transactional
	@Scheduled(cron = "0 0 0 * * ?") // A minuit
	public void resetHabitCheckedStatus() {

		habitService.refreshHabits();
		expeditionService.refreshCombatInstance();
		buildingService.refreshMarketItemPool();

	}

	@Transactional
	@Scheduled(cron = "0 */5 * * * *") // Toutes les 5 mins
	public void healRecoveringCharacters() {

		characterService.healCharacter();

	}

}
