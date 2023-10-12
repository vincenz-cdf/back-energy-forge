package com.vinz.energy.EnergyForge.dto;

import java.util.Set;

import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;

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