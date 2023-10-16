package com.vinz.energy.EnergyForge.dto;

import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.db.enums.CombatStatus;
import com.vinz.energy.EnergyForge.db.enums.Scenario;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CombatInstanceDto {

	private Long id;
	
	private String label;
	
	private CombatStatus status;
    
	private Long userId;
    
	private List<CombatIndexes> indexes = new ArrayList<CombatIndexes>();

	private Scenario scenario;
   
	private List<Long> characterIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CombatStatus getStatus() {
		return status;
	}

	public void setStatus(CombatStatus status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CombatIndexes> getIndexes() {
		return indexes;
	}

	public void setIndexes(List<CombatIndexes> indexes) {
		this.indexes = indexes;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public List<Long> getCharacterIds() {
		return characterIds;
	}

	public void setCharacterIds(List<Long> characterIds) {
		this.characterIds = characterIds;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
