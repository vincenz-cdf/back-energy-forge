package com.vinz.energy.EnergyForge.mappers;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.dto.EquipmentDto;
import com.vinz.energy.EnergyForge.dto.EquippedItemDto;

@Mapper(componentModel = "spring")
public interface EquippedItemMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "character.id", target = "characterId")
	@Mapping(source = "equipment.id", target = "equipmentId")
	@Mapping(source = "slot", target = "slot")
	EquippedItemDto toDto(EquippedItem equippedItem);

    @Mapping(source = "id", target = "id")
	@Mapping(source = "characterId", target = "character.id")
	@Mapping(source = "equipmentId", target = "equipment.id")
    @Mapping(source = "slot", target = "slot")
    EquippedItem toEntity(EquippedItemDto equippedItemDto);

    Set<EquippedItemDto> toDtoList(Set<EquippedItem> equippedItems);

    Set<EquippedItem> toEntityList(Set<EquippedItemDto> equippedItemDtos);
}

