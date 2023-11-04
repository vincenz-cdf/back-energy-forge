package com.vinz.energy.EnergyForge.dto.equipments.abilities;


import com.vinz.energy.EnergyForge.dto.equipments.ArmorDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArmorAbilityDto {
	private Long id;
	private ArmorDto armor;
	private EquipmentAbilityDto ability;
	
}
