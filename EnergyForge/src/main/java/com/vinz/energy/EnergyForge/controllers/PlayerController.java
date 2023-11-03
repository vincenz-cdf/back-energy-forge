package com.vinz.energy.EnergyForge.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vinz.energy.EnergyForge.dto.BuildingDto;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.HabitDto;
import com.vinz.energy.EnergyForge.service.BuildingService;
import com.vinz.energy.EnergyForge.service.CharacterService;
import com.vinz.energy.EnergyForge.service.EquipmentService;
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
	private CharacterService characterService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private EquipmentService equipmentService;

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
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve habits", e);
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
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create habit", e);
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
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update habit", e);
		}
	}

	@GetMapping("/characters")
	public ResponseEntity<Map<String, Object>> showCharacters() {
		try {
			Long userId = global.getCurrentUserId();

			List<CharacterDto> chars = characterService.showCharacters(userId);

			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Chars successfully retrieved.");
			response.put("data", chars);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve chars", e);
		}
	}

	@DeleteMapping("/habits/{id}")
	public ResponseEntity<Map<String, String>> deleteHabit(@PathVariable Long id) {
		try {
			habitService.deleteHabit(id);
			Map<String, String> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Habit successfully deleted.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete habit", e);
		}
	}

	@PutMapping("/characters/{id}/heal")
	public ResponseEntity<Map<String, String>> healWoundedCharacter(@PathVariable Long id) {
		try {
			Long userId = global.getCurrentUserId();

			characterService.healCharacterFromTemple(id, userId);

			Map<String, String> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Char successfully healed.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to heal char", e);
		}
	}
	
	@PutMapping("/characters/{id}/drink")
	public ResponseEntity<Map<String, String>> drinkDemoralizedCharacter(@PathVariable Long id) {
		try {
			Long userId = global.getCurrentUserId();

			characterService.drinkCharacterFromTavern(id, userId);

			Map<String, String> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Char successfully healed.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to heal char", e);
		}
	}

	@PutMapping("/characters/{charId}/consume/{id}")
	public ResponseEntity<Map<String, Object>> consumeRecoverChar(@PathVariable Integer id, @PathVariable Long charId) {
		try {
			Long userId = global.getCurrentUserId();

			CharacterDto character = characterService.setCharacterRecoverHeal(id, charId, userId);

			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Char successfully set recovering.");
			response.put("data", character);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to set recover char", e);
		}
	}
	
	@GetMapping("/buildings")
	public ResponseEntity<Map<String, Object>> showBuildings() {
		try {
			Long userId = global.getCurrentUserId();

			List<BuildingDto> buildings = buildingService.retrieveBuildingsForUser(userId);

			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Buildings successfully retrieved.");
			response.put("data", buildings);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve buildings", e);
		}
	}
	
	@PostMapping("/buy/{id}/{type}")
	public ResponseEntity<Map<String, String>> buyItem(@PathVariable Long id, @PathVariable String type) {
		try {
			
			Long userId = global.getCurrentUserId();
			
			equipmentService.buyItem(id, type, userId);
			
			Map<String, String> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Habit successfully update.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to buy item", e);
		}
	}

}
