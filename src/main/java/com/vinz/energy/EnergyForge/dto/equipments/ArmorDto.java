package com.vinz.energy.EnergyForge.dto.equipments;

import java.io.Serializable;
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
public class ArmorDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private EquipmentDto equipment;
	private Integer armorValue;
	private Set<ArmorAbilityDto> armorAbilities;
}