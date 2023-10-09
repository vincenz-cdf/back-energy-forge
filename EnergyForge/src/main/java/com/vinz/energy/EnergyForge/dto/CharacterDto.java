package com.vinz.energy.EnergyForge.dto;

import com.vinz.energy.EnergyForge.db.enums.CharacterType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterDto {

	private Long id;
	private String name;
	private Integer hp;
	private Integer armor;
	private Integer atk;
	private String spritePath;
	private CharacterType characterType;
	private Long userId;
	
}
