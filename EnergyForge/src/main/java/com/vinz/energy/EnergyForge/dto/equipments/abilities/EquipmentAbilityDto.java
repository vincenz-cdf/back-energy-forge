package com.vinz.energy.EnergyForge.dto.equipments.abilities;


import com.vinz.energy.EnergyForge.db.enums.Effect;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EquipmentAbilityDto {

	private Integer id;
	private String name;
	private String description;
	private Integer value;
	private Integer secondaryValue = null;
	
	private Effect effect;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getSecondaryValue() {
		return secondaryValue;
	}

	public void setSecondaryValue(Integer secondaryValue) {
		this.secondaryValue = secondaryValue;
	}

	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
	}
	
	
}
