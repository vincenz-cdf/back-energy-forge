package com.vinz.energy.EnergyForge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentDto {
	private Long id;
	private String name;
	private Integer value;
	private String spritePath;
}
