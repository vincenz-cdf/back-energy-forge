package com.vinz.energy.EnergyForge.dto;


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
	private ArmorDto dto;
	private EquipmentAbilityDto ability;
	
}
