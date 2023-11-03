package com.vinz.energy.EnergyForge.dto;

import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.dto.equipments.EquipmentDto;

public class MarketItemPool {
	
	private List<EquipmentDto> equipments = new ArrayList<EquipmentDto>();
	private List<ConsumableDto> consumables = new ArrayList<ConsumableDto>();

	public List<EquipmentDto> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<EquipmentDto> equipments) {
		this.equipments = equipments;
	}

	public List<ConsumableDto> getConsumables() {
		return consumables;
	}

	public void setConsumables(List<ConsumableDto> consumables) {
		this.consumables = consumables;
	}
	
	

}
