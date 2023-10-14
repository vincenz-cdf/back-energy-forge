package com.vinz.energy.EnergyForge.dto.equipments;

import java.util.Set;

import com.vinz.energy.EnergyForge.dto.equipments.abilities.ArmorAbilityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArmorDto {
	private Long id;
	private EquipmentDto equipment;
	private Integer armorValue;
	private Set<ArmorAbilityDto> armorAbilities;
}