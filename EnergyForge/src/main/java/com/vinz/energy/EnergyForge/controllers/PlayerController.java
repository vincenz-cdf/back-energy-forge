package com.vinz.energy.EnergyForge.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinz.energy.EnergyForge.service.HabitService;
import com.vinz.energy.EnergyForge.service.ProfileService;

@RestController
public class PlayerController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private HabitService habitService;
	
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> profile(@PathVariable Long id) {
		return ResponseEntity.ok(profileService.findById(id));
	}
	
    @PostMapping("/habits")
    public ResponseEntity<?> createHabit() {
    	System.out.println(profileService.getCurrentUserId());
        return ResponseEntity.ok(null);
    }
}
