package com.vinz.energy.EnergyForge.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.vinz.energy.EnergyForge.db.Habit;
import com.vinz.energy.EnergyForge.dto.HabitDto;

@Component
public class DataUtil {

    public HabitDto convertToDto(Habit habit) {
        HabitDto dto = new HabitDto();
        dto.setId(habit.getId());
        dto.setTitle(habit.getTitle());
        dto.setChecked(habit.getChecked());
        dto.setUserId(habit.getUser().getId());
        return dto;
    }

    public List<HabitDto> convertToDtoList(List<Habit> habits) {
        return habits.stream()
                     .map(this::convertToDto)
                     .collect(Collectors.toList());
    }
}

