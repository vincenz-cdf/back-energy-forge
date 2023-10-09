package com.vinz.energy.EnergyForge.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dto.UserDto;


@Service
public class Global {
	
    public static String[] defaultCharNames = {
            "Wesley", "Woody", "Mino", "Karl", "Cecco", "Wilbur", "Kostja", "Miles",
            "Rawls", "Kylan", "Silvester", "Endrik", "Hamlyn", "Jadyn", "Tearlach", "Deshawn"
        };

	public Long getCurrentUserId() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if(authentication == null || !authentication.isAuthenticated()) {
	        throw new RuntimeException("User not authenticated");
	    }
	    
	    Object principal = authentication.getPrincipal();
	    
	    // Ensure principal is of type UserDto and has a non-null id
	    if (!(principal instanceof UserDto)) {
	        throw new RuntimeException("User details not found in context");
	    }
	    
	    UserDto userPrincipal = (UserDto) principal;
	    if (userPrincipal.getId() == null) {
	        throw new RuntimeException("User ID not found in context");
	    }
	    
	    return userPrincipal.getId();
	}


}
