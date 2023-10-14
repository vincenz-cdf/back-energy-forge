package com.vinz.energy.EnergyForge.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.dto.equipments.ArmorDto;

@Mapper(componentModel = "spring", uses = {ArmorAbilityMapper.class, EquippedItemMapper.class, UserItemMapper.class})
public interface ArmorMapper {

    @Mapping(target = "armorAbilities", ignore = true)  // Avoiding circular mapping for now
    ArmorDto toDto(Armor armor);

    @Mapping(target = "armorAbilities", ignore = true)  // Avoiding circular mapping for now
    Armor toEntity(ArmorDto armorDto);

    List<ArmorDto> toDtoList(List<Armor> armors);

    List<Armor> toEntityList(List<ArmorDto> armorDtos);
}


