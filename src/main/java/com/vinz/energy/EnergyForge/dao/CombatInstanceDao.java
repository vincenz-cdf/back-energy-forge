package com.vinz.energy.EnergyForge.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinz.energy.EnergyForge.db.CombatInstance;
import com.vinz.energy.EnergyForge.db.enums.CombatStatus;
import com.vinz.energy.EnergyForge.db.enums.Scenario;

public interface CombatInstanceDao extends JpaRepository<CombatInstance, Long> {
	
	Optional<CombatInstance> findByUserIdAndScenarioAndStatusIn(Long userId, Scenario scenario, List<CombatStatus> status);
	
	List<CombatInstance> findByStatus(CombatStatus status);
	
	List<CombatInstance> findByStatusAndScenario(CombatStatus status, Scenario scenario);
	
	List<CombatInstance> findByUserIdAndScenarioNotOrderByIdAsc(Long userId, Scenario scenario);
	
	List<CombatInstance> findByUserIdAndScenarioAndStatusNotOrderByIdAsc(Long userId, Scenario scenario, CombatStatus status);

}
