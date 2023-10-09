package com.vinz.energy.EnergyForge.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.vinz.energy.EnergyForge.db.Habit;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.dto.HabitDto;


@Mapper(componentModel="spring")
public interface HabitMapper {

    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUser")
    Habit toHabit(HabitDto habitDto);

    @Mapping(target = "userId", source = "user.id")
    HabitDto toHabitDto(Habit habit);

    @Named("idToUser")
    default User idToUser(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
    
    default List<HabitDto> toHabitDtos(List<Habit> habits) {
        return habits.stream()
                     .map(this::toHabitDto)
                     .collect(Collectors.toList());
    }

}

