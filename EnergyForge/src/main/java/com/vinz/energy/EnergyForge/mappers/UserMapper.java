package com.vinz.energy.EnergyForge.mappers;

import org.mapstruct.Mapper;

import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.dto.UserDto;

@Mapper(componentModel="spring")
public interface UserMapper {
	
	UserDto toUserDto(User user);
}
