package com.vinz.energy.EnergyForge.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.equipments.abilities.ArmorAbility;
import com.vinz.energy.EnergyForge.dto.ArmorAbilityDto;

@Mapper(componentModel = "spring", uses = {ArmorMapper.class, EquipmentAbilityMapper.class, EquippedItemMapper.class,})
public interface ArmorAbilityMapper {

    @Mapping(source = "armor.id", target = "dto.id")
    @Mapping(source = "ability", target = "ability")
    ArmorAbilityDto toDto(ArmorAbility armorAbility);

    @Mapping(source = "dto.id", target = "armor.id")
    @Mapping(source = "ability", target = "ability")
    ArmorAbility toEntity(ArmorAbilityDto armorAbilityDto);

    List<ArmorAbilityDto> toDtoList(List<ArmorAbility> armorAbilities);

    List<ArmorAbility> toEntityList(List<ArmorAbilityDto> armorAbilityDtos);
}


