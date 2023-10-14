package com.vinz.energy.EnergyForge.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.dto.equipments.EquippedItemDto;

@Mapper(componentModel = "spring")
public interface EquippedItemMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "character.id", target = "characterId")
	@Mapping(source = "equipment.id", target = "equipmentId")
	@Mapping(source = "equipment.name", target = "equipmentName")
	@Mapping(source = "equipment.value", target = "equipmentValue")
	@Mapping(source = "equipment.spritePath", target = "equipmentSpritePath")
	@Mapping(source = "slot", target = "slot")
	EquippedItemDto toDto(EquippedItem equippedItem);

    @Mapping(source = "id", target = "id")
	@Mapping(source = "characterId", target = "character.id")
	@Mapping(source = "equipmentId", target = "equipment.id")
	@Mapping(target = "equipment.name", source = "equipmentName")
	@Mapping(target = "equipment.value", source = "equipmentValue")
	@Mapping(target = "equipment.spritePath", source = "equipmentSpritePath")
    @Mapping(source = "slot", target = "slot")
    EquippedItem toEntity(EquippedItemDto equippedItemDto);

    List<EquippedItemDto> toDtoList(List<EquippedItem> equippedItems);

    List<EquippedItem> toEntityList(List<EquippedItemDto> equippedItemDtos);
}

