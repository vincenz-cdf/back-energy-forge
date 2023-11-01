package com.vinz.energy.EnergyForge.service.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetHabitCheckedStatus() {
    	
    	habitService.refreshHabits();
    	expeditionService.refreshCombatInstance();
    	
    }
    
    @Transactional
    @Scheduled(cron = "0 */5 * * * *")
    public void healRecoveringCharacters() {
    	
    	characterService.healCharacter();
    	
    }
    
}
