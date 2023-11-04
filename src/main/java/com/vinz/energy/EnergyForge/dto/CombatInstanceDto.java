package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.db.CombatInstance;
import com.vinz.energy.EnergyForge.db.enums.CombatStatus;
import com.vinz.energy.EnergyForge.db.enums.Scenario;


public class CombatInstanceDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String label;
	
	private CombatStatus status;
    
	private Long userId;
    
	private List<CombatIndexes> indexes = new ArrayList<CombatIndexes>();

	private Scenario scenario;
   
	private List<CharacterDto> characters = new ArrayList<CharacterDto>();
	
	private CumulatedTeamStats afterMathInfo;
	
	private CombatReward combatReward;
	
	public CombatInstanceDto() {
		
	}
	
	public CombatInstanceDto(CombatInstance ci) {
		this.id = ci.getId();
		this.label = ci.getLabel();
		this.status = ci.getStatus();
		this.userId = ci.getUser().getId();
		this.indexes = ci.getIndexes();
		this.scenario = ci.getScenario();
		this.characters = new ArrayList<CharacterDto>();
		
		for(com.vinz.energy.EnergyForge.db.Character c : ci.getCharacters()) {
			this.characters.add(new CharacterDto(c));
		}
		
		this.afterMathInfo = ci.getAfterMathInfo();
		this.combatReward = ci.getCombatReward();
	}

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

	public List<CharacterDto> getCharacters() {
		return characters;
	}

	public void setCharacters(List<CharacterDto> characters) {
		this.characters = characters;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public CumulatedTeamStats getAfterMathInfo() {
		return afterMathInfo;
	}

	public void setAfterMathInfo(CumulatedTeamStats afterMathInfo) {
		this.afterMathInfo = afterMathInfo;
	}

	public CombatReward getCombatReward() {
		return combatReward;
	}

	public void setCombatReward(CombatReward combatReward) {
		this.combatReward = combatReward;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
