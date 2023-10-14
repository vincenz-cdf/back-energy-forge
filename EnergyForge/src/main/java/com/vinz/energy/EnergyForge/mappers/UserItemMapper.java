package com.vinz.energy.EnergyForge.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.UserItem;
import com.vinz.energy.EnergyForge.dto.UserItemDto;

@Mapper(componentModel = "spring")
public interface UserItemMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "equipment.id", target = "equipmentId")
	UserItemDto toDto(UserItem userItem);
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "equipment.id", source = "equipmentId")
	UserItem toEntity(UserItemDto userItemDto);
	
}