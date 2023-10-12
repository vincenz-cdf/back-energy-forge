package com.vinz.energy.EnergyForge.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.dto.CharacterDto;

@Mapper(componentModel="spring", uses = EquippedItemMapper.class)
public interface CharacterMapper {
	
    
    @Mapping(source = "id", target = "id")
    @Mapping(target = "userId", source = "user.id")
    CharacterDto toDto(com.vinz.energy.EnergyForge.db.Character character);

    default List<CharacterDto> toCharacterDtos(List<com.vinz.energy.EnergyForge.db.Character> chars) {
        return chars.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
    }

}
