package com.vinz.energy.EnergyForge.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.vinz.energy.EnergyForge.db.CombatInstance;
import com.vinz.energy.EnergyForge.dto.CombatInstanceDto;

@Mapper(componentModel="spring")
public interface CombatInstanceMapper {
	
    @Mapping(target = "characterIds", source = "characters", qualifiedByName = "charactersToIds")
    @Mapping(target = "userId", source = "user.id")
    CombatInstanceDto toDto(CombatInstance entity);
	
    @Named("charactersToIds")
    default List<Long> charactersToIds(List<com.vinz.energy.EnergyForge.db.Character> characters) {
        if (characters == null) {
            return null;
        }
        return characters.stream()
            .map(com.vinz.energy.EnergyForge.db.Character::getId)
            .collect(Collectors.toList());
    }

}
