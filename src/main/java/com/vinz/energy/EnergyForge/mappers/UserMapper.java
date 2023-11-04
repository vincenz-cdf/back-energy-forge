package com.vinz.energy.EnergyForge.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.dto.UserDto;
import com.vinz.energy.EnergyForge.dto.auth.SignUpDto;

@Mapper(componentModel = "spring" , uses = {UserItemMapper.class})
public interface UserMapper {
	
    @Mapping(source = "userItems", target = "userItems")
	UserDto toDto(User user);
    
    /*
    @Mapping(source = "userItems", target = "userItems")
    User toEntity(UserDto userDto);
    */
	
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
