package com.vinz.energy.EnergyForge.dto;


import com.vinz.energy.EnergyForge.db.enums.Effect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentAbilityDto {

	private Long id;
	private String name;
	private String description;
	private Integer value;
	
	private Effect effect;
}
