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
    private List<Integer> consumableIds;
    private int diamonds = 0;
    
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
	public List<Integer> getConsumableIds() {
		return consumableIds;
	}
	public void setConsumableIds(List<Integer> consumableIds) {
		this.consumableIds = consumableIds;
	}
	public int getDiamonds() {
		return diamonds;
	}
	public void setDiamonds(int diamonds) {
		this.diamonds = diamonds;
	}
	

    // Constructors, getters, setters, etc.
    
    
}
