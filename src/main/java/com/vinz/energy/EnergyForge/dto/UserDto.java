package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;
	private Long id;
    private String email;
    private String login;
    private String token;

    private Integer energy;
    private Integer coin;
    
    private List<UserItemDto> userItems;
    
    
}