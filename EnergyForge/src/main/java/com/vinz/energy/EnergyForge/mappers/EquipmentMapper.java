package com.vinz.energy.EnergyForge.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.dto.EquipmentDto;

@Mapper(componentModel = "spring", uses = {EquippedItemMapper.class})
public interface EquipmentMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "spritePath", target = "spritePath")
    @Mapping(source = "slot", target = "slot")
    @Mapping(source = "equippedItems", target = "equippedItems")
    EquipmentDto toDto(Equipment equipment);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "spritePath", target = "spritePath")
    @Mapping(source = "equippedItems", target = "equippedItems")
    Equipment toEntity(EquipmentDto equipmentDto);

    List<EquipmentDto> toDtoList(List<Equipment> equipments);

    List<Equipment> toEntityList(List<EquipmentDto> equipmentDtos);
}

