package com.vinz.energy.EnergyForge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitDto {

    private Long id;
    private String title;
    private Boolean checked;
    private Long userId;
    
}