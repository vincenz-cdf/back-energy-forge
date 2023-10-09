package com.vinz.energy.EnergyForge.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.dto.CharacterDto;

@Mapper(componentModel="spring")
public interface CharacterMapper {
	
    @Mapping(target = "userId", source = "user.id")
    CharacterDto toCharacterDto(com.vinz.energy.EnergyForge.db.Character chars);

    default List<CharacterDto> toCharacterDtos(List<com.vinz.energy.EnergyForge.db.Character> chars) {
        return chars.stream()
                     .map(this::toCharacterDto)
                     .collect(Collectors.toList());
    }
}
