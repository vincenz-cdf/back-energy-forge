package com.vinz.energy.EnergyForge.dto;

import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.dto.equipments.ArmorDto;
import com.vinz.energy.EnergyForge.dto.equipments.WeaponDto;

public class MarketItemPool {
	
	private List<ConsumableDto> consumables = new ArrayList<ConsumableDto>();
	private List<WeaponDto> weapons = new ArrayList<>();
	private List<ArmorDto> armors = new ArrayList<>();


	public List<WeaponDto> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<WeaponDto> weapons) {
		this.weapons = weapons;
	}

	public List<ArmorDto> getArmors() {
		return armors;
	}

	public void setArmors(List<ArmorDto> armors) {
		this.armors = armors;
	}

	public List<ConsumableDto> getConsumables() {
		return consumables;
	}

	public void setConsumables(List<ConsumableDto> consumables) {
		this.consumables = consumables;
	}
	
	

}
