package com.vinz.energy.EnergyForge.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.vinz.energy.EnergyForge.db.equipments.abilities.EquipmentAbility;
import com.vinz.energy.EnergyForge.dto.equipments.abilities.EquipmentAbilityDto;

@Mapper(componentModel = "spring")
public interface EquipmentAbilityMapper {

    EquipmentAbilityDto toDto(EquipmentAbility equipmentAbility);

    EquipmentAbility toEntity(EquipmentAbilityDto equipmentAbilityDto);

    List<EquipmentAbilityDto> toDtoList(List<EquipmentAbility> equipmentAbilities);

    List<EquipmentAbility> toEntityList(List<EquipmentAbilityDto> equipmentAbilityDtos);
}

