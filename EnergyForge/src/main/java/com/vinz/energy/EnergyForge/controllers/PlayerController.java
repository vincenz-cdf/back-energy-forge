package com.vinz.energy.EnergyForge.controllers;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vinz.energy.EnergyForge.dto.HabitDto;
import com.vinz.energy.EnergyForge.service.Global;
import com.vinz.energy.EnergyForge.service.HabitService;
import com.vinz.energy.EnergyForge.service.ProfileService;

@RestController
public class PlayerController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private HabitService habitService;
	
	@Autowired
	private Global global;
	
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> profile(@PathVariable Long id) {
		return ResponseEntity.ok(profileService.findById(id));
	}
	
	@GetMapping("/habits")
	public ResponseEntity<Map<String, Object>> showHabits() {
	    try {
	        Long userId = global.getCurrentUserId();
	        
	        List<HabitDto> habits = habitService.showHabits(userId);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Habits successfully retrieved.");
	        response.put("data", habits); 
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR, 
	            "Unable to retrieve habits",
	            e
	        );
	    }
	}

	
    
    @PostMapping("/habits")
    public ResponseEntity<Map<String, String>> createHabit() {
        try {
            Long userId = global.getCurrentUserId();
            
            habitService.createEmptyHabit(userId);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Habit successfully created.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Unable to create habit",
                e
            );
        }
    }
    
    @PostMapping("/habits/{id}")
    public ResponseEntity<Map<String, String>> updateHabit(@PathVariable Long id, @RequestBody HabitDto dto) {
        try {
            habitService.updateHabit(id, dto);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Habit successfully update.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Unable to update habit",
                e
            );
        }
    }
}
