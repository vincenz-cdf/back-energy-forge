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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.CombatInstanceDto;
import com.vinz.energy.EnergyForge.dto.api.RequestStringDto;
import com.vinz.energy.EnergyForge.service.ExpeditionService;
import com.vinz.energy.EnergyForge.service.Global;

@RestController
public class ExpeditionController {

	@Autowired
	private ExpeditionService expeditionService;

	@Autowired
	private Global global;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpeditionController.class);

	@PostMapping("/combatinstance")
	public ResponseEntity<Map<String, Object>> createOrRetrieveCombatI(@RequestBody RequestStringDto scenarioEnum) {
		try {
			Long userId = global.getCurrentUserId();

			CombatInstanceDto dto = expeditionService.createOrRetrieveCombatI(scenarioEnum.getString(), userId);

			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Combat instance successfully retrieved.");
			response.put("data", dto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve Combat instance",
					e);
		}
	}
	
	

	@GetMapping("/combatinstance/{id}/characters")
	public ResponseEntity<Map<String, Object>> getCharactersForCombat(@PathVariable Long id) {
		try {

			List<CharacterDto> dto = expeditionService.getCharactersForCombat(id);

			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Chars forcombat instance successfully retrieved.");
			response.put("data", dto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrieve Combat instance chars", e);
		}
	}

	@GetMapping("/combatinstance/currentCharacters/{fromHabit}")
	public ResponseEntity<Map<String, Object>> listCurrentCharacters(@PathVariable boolean fromHabit) {
		try {
			Long userId = global.getCurrentUserId();

			List<CharacterDto> dto = expeditionService.listCurrentCharacters(userId, fromHabit);

			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Chars successfully retrieved.");
			response.put("data", dto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve chars", e);
		}
	}

	@PutMapping("/combatinstance/{ciId}/character/{charId}")
	public ResponseEntity<Map<String, String>> putHeroCharacterInCombat(@PathVariable Long ciId,
			@PathVariable Long charId) {
		try {
			expeditionService.putHeroCharacterInCombat(ciId, charId);
			Map<String, String> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Character successfully added to combat instance.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to add character to combat instance", e);
		}
	}

	@PostMapping("/combatinstance/{id}/launch")
	public ResponseEntity<Map<String, String>> launchCombatInstance(@PathVariable Long id) {
		try {
			
			Long userId = global.getCurrentUserId();

			expeditionService.calculateCombatResult(id, userId);

			Map<String, String> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Combat successfully done.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fight", e);
		}
	}
	
	@GetMapping("/combatinstance/{id}")
	public ResponseEntity<Map<String, Object>> getCombatIntance(@PathVariable Long id) {
		try {

			CombatInstanceDto dto = expeditionService.getCombatInstance(id);

			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Chars successfully retrieved.");
			response.put("data", dto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve chars", e);
		}
	}
	
	@PutMapping("/combatinstance/{id}/reward")
	public ResponseEntity<Map<String, String>> getCombatIntanceResults(@PathVariable Long id) {
		try {

			Long userId = global.getCurrentUserId();
			
			expeditionService.gatherCombatReward(id, userId);

			Map<String, String> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Chars successfully retrieved.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve chars", e);
		}
	}
	
	@GetMapping("/combatinstance/user")
	public ResponseEntity<Map<String, Object>> getCombatIntanceForUser() {
		try {
			
			Long userId = global.getCurrentUserId();
			
			List<CombatInstanceDto> dto = expeditionService.getCombatInstanceForUser(userId);

			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Chars successfully retrieved.");
			response.put("data", dto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve chars", e);
		}
	}
}
