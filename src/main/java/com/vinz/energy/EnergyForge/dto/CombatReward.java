package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.dto.equipments.EquipmentDto;


public class CombatReward implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer coins = 0;
	private Integer experiencePoints = 0;
	private List<EquipmentDto> equipments = new ArrayList<EquipmentDto>();
	private List<ConsumableDto> consumables = new ArrayList<ConsumableDto>();
	
	public Integer getCoins() {
		return coins;
	}
	public void setCoins(Integer coins) {
		this.coins = coins;
	}
	public Integer getExperiencePoints() {
		return experiencePoints;
	}
	public void setExperiencePoints(Integer experiencePoints) {
		this.experiencePoints = experiencePoints;
	}
	
	public List<EquipmentDto> getEquipments() {
		return equipments;
	}
	public void setEquipments(List<EquipmentDto> equipments) {
		this.equipments = equipments;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<ConsumableDto> getConsumables() {
		return consumables;
	}
	public void setConsumables(List<ConsumableDto> consumables) {
		this.consumables = consumables;
	}
	
}
