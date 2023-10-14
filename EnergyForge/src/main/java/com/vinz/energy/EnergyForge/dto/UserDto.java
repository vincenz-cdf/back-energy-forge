package com.vinz.energy.EnergyForge.dto;

import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.db.UserItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String email;
    private String login;
    private String token;

    private Integer energy;
    private Integer coin;
    
    /*
    @Builder.Default
    private List<UserItemDto> userItems = new ArrayList<UserItemDto>();
    */
    
    
    
}