package com.vinz.energy.EnergyForge.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.equipments.ArmorDto;
import com.vinz.energy.EnergyForge.dto.equipments.WeaponDto;
import com.vinz.energy.EnergyForge.service.EquipmentService;
import com.vinz.energy.EnergyForge.service.ExpeditionService;
import com.vinz.energy.EnergyForge.service.Global;

@RestController
public class EquipmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExpeditionService.class);
	
	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private Global global;
	
	@GetMapping("/armors")
	public ResponseEntity<Map<String, Object>> showAllArmors() {
	    try {
	        Long userId = global.getCurrentUserId();
	        
	        //D'un user
	        List<ArmorDto> armors = equipmentService.getOwnedArmors(userId);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Habits successfully retrieved.");
	        response.put("data", armors); 
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	    	logger.error(e.getMessage());
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR, 
	            "Unable to retrieve armors",
	            e
	        );
	    }
	}
	
	@GetMapping("/weapons")
	public ResponseEntity<Map<String, Object>> showAllWeapons() {
	    try {
	        Long userId = global.getCurrentUserId();
	        
	        //D'un user
	        List<WeaponDto> weapons = equipmentService.getOwnedWeapons(userId);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Habits successfully retrieved.");
	        response.put("data", weapons); 
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	    	logger.error(e.getMessage());
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR, 
	            "Unable to retrieve weapons",
	            e
	        );
	    }
	}
	
	@PostMapping("/equipments/{charId}")
	public ResponseEntity<Map<String, Object>> equipCharacterWithGear(@PathVariable Long charId, @RequestBody Long gearId) {
	    try {
	        CharacterDto charDto = equipmentService.equipCharacterWithSpecificGear(charId, gearId);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Gear successfully equipped.");
            response.put("data", charDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	    	logger.error(e.getMessage());
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR, 
	            "Unable to equip",
	            e
	        );
	    }
	}
	
	/*
	@GetMapping("/equippeditem/{charId}")
	public ResponseEntity<Map<String, Object>>equipCharacterWithGear(@PathVariable Long charId) {
	    try {
	        Set<EquippedItemDto> equippedItems = equipmentService.listEquippedItemByCharId(charId);
	        
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Gears successfully fetched.");
	        response.put("data", equippedItems); 
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR, 
	            "Unable to equip",
	            e
	        );
	    }
	}
	*/
}
