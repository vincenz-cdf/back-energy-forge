package com.vinz.energy.EnergyForge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.CombatInstanceDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.CombatInstance;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.enums.CombatStatus;
import com.vinz.energy.EnergyForge.db.enums.Scenario;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.CombatIndexes;
import com.vinz.energy.EnergyForge.dto.CombatInstanceDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.CharacterMapper;
import com.vinz.energy.EnergyForge.mappers.CombatInstanceMapper;

@Service
public class ExpeditionService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExpeditionService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private CharacterMapper characterMapper;
	
	@Autowired
	private CombatInstanceDao combatInstanceDao;
	
	@Autowired
	private CombatInstanceMapper combatInstanceMapper;

	private Random random = new Random();

	public CombatInstanceDto createOrRetrieveCombatI(String scenarioEnum, Long userId) {
		
		try {

			Scenario scenario = Scenario.valueOf(scenarioEnum);
			User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
			boolean isCampaign = scenario.isCampaign();

			CombatInstance ci = combatInstanceDao.findByUserIdAndScenarioAndStatus(userId, scenario, CombatStatus.AWAIT).orElse(null);
			
			if(ci == null) {
				if (isCampaign) {
					CombatInstance combatInstance = new CombatInstance();
					combatInstance.setScenario(scenario);
					combatInstance.setLabel(scenario.traductEnumToLabel());
					combatInstance.setStatus(CombatStatus.AWAIT);
					combatInstance.setUser(user);

					List<Character> chars = pregenerateNpcsForCampaign(scenario);

					combatInstance.setCharacters(chars);
					combatInstance.setIndexes(new ArrayList<CombatIndexes>());

					combatInstanceDao.save(combatInstance);
					
					return combatInstanceMapper.toDto(combatInstance);
				} else {
					//...opencontracts
					//combatInstance.setIndexes(calculateNpcIndexes(chars));
				}
			}

			return combatInstanceMapper.toDto(ci);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	private List<Character> pregenerateNpcsForCampaign(Scenario scenario) {
		List<Character> characters = new ArrayList<Character>();
		switch (scenario) {
		case T_L1:
			characters.addAll(characterDao.findByCharacterType(CharacterType.TRAINING1));
			break;
		default:
			break;

		}

		return characters;
	}

	private List<CombatIndexes> calculateNpcIndexes(List<Character> chars) {

		List<CombatIndexes> indexes = new ArrayList<CombatIndexes>();
		List<Equipment> leftHandEquipment = new ArrayList<>();
		List<Equipment> rightHandEquipment = new ArrayList<>();

		for (int i = 0; i < chars.size(); i++) {

			CombatIndexes ci = new CombatIndexes();
			ci.setCharacterIndex(i);

			for (EquippedItem ei : chars.get(i).getEquippedItems()) {
				if (ei.getEquipment().getSlot() == EquipmentSlot.LEFTHAND
						|| ei.getEquipment().getSlot() == EquipmentSlot.ZWEIHAND) {
					leftHandEquipment.add(ei.getEquipment());
				}
				if (ei.getEquipment().getSlot() == EquipmentSlot.RIGHTHAND) {
					rightHandEquipment.add(ei.getEquipment());
				}
			}

			int weaponIndex = random.nextInt(leftHandEquipment.size());
			ci.setWeaponIndex(weaponIndex);

			if (leftHandEquipment.get(weaponIndex).getSlot() != EquipmentSlot.ZWEIHAND) {
				ci.setShieldIndex(random.nextInt(leftHandEquipment.size()));
			} else {
				ci.setShieldIndex(null);
			}

			indexes.add(ci);
		}

		return indexes;
	}
	
	public List<CharacterDto> getCharactersForCombat(Long id) {
		
		CombatInstance combatInstance = combatInstanceDao.findById(id)
		        .orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));
		
		return characterMapper.toCharacterDtos(combatInstance.getCharacters());
	}
	
	public List<CharacterDto> listCurrentCharacters(Long userId) {
		
		return characterMapper.toCharacterDtos(characterDao.findByUserId(userId));	
	}
	
	public void putHeroCharacterInCombat(Long ciId, Long charId) {
		CombatInstance combatInstance = combatInstanceDao.findById(ciId)
		        .orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));
		
		Character character = characterDao.findById(charId)
		        .orElseThrow(() -> new AppException("Unknown combat instance", HttpStatus.NOT_FOUND));
		
		if(combatInstance.getCharacters().contains(character)) {
			combatInstance.getCharacters().remove(character);
		} else {
			combatInstance.getCharacters().add(character);
		}

		combatInstanceDao.save(combatInstance);

	}
}
