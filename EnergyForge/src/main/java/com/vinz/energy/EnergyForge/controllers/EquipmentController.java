package com.vinz.energy.EnergyForge.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.dto.ArmorDto;
import com.vinz.energy.EnergyForge.dto.EquippedItemDto;
import com.vinz.energy.EnergyForge.service.EquipmentService;
import com.vinz.energy.EnergyForge.service.Global;

@RestController
public class EquipmentController {
	
	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private Global global;
	
	@GetMapping("/equipments")
	public ResponseEntity<Map<String, Object>> showAllEquipments() {
	    try {
	        Long userId = global.getCurrentUserId();
	        
	        //D'un user
	        List<ArmorDto> armors = equipmentService.getAllArmors();
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Habits successfully retrieved.");
	        response.put("data", armors); 
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR, 
	            "Unable to retrieve armors",
	            e
	        );
	    }
	}
	
	@PostMapping("/equipments/{charId}")
	public ResponseEntity<Map<String, String>> equipCharacterWithGear(@PathVariable Long charId, @RequestBody Long gearId) {
	    try {
	        equipmentService.equipCharacterWithSpecificGear(charId, gearId);
	        
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Gear successfully equipped.");
            return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
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
