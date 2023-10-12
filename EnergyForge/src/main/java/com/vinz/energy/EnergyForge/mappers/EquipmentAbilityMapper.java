package com.vinz.energy.EnergyForge.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.equipments.abilities.EquipmentAbility;
import com.vinz.energy.EnergyForge.dto.EquipmentAbilityDto;

@Mapper(componentModel = "spring")
public interface EquipmentAbilityMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "effect", target = "effect")
    EquipmentAbilityDto toDto(EquipmentAbility equipmentAbility);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "effect", target = "effect")
    EquipmentAbility toEntity(EquipmentAbilityDto equipmentAbilityDto);

    List<EquipmentAbilityDto> toDtoList(List<EquipmentAbility> equipmentAbilities);

    List<EquipmentAbility> toEntityList(List<EquipmentAbilityDto> equipmentAbilityDtos);
}

