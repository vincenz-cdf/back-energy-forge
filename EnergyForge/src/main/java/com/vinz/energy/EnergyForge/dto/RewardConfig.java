package com.vinz.energy.EnergyForge.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RewardConfig {
    private int experiencePoints;
    private int coins;
    private List<Long> equipmentIds;
    private List<Long> consumableIds;
	public int getExperiencePoints() {
		return experiencePoints;
	}
	public void setExperiencePoints(int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public List<Long> getEquipmentIds() {
		return equipmentIds;
	}
	public void setEquipmentIds(List<Long> equipmentIds) {
		this.equipmentIds = equipmentIds;
	}
	public List<Long> getConsumableIds() {
		return consumableIds;
	}
	public void setConsumableIds(List<Long> consumableIds) {
		this.consumableIds = consumableIds;
	}

    // Constructors, getters, setters, etc.
    
    
}
