package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitDto implements Serializable {

    private static final long serialVersionUID = 1L;
	private Long id;
    private String title;
    private Boolean checked;
    private Long userId;
    
}