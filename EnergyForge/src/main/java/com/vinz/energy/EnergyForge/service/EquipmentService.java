package com.vinz.energy.EnergyForge.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinz.energy.EnergyForge.dao.ArmorDao;
import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.dao.EquippedItemDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.db.equipments.Weapon;
import com.vinz.energy.EnergyForge.dto.ArmorDto;
import com.vinz.energy.EnergyForge.dto.EquippedItemDto;
import com.vinz.energy.EnergyForge.dto.WeaponDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.ArmorMapper;
import com.vinz.energy.EnergyForge.mappers.EquippedItemMapper;
import com.vinz.energy.EnergyForge.mappers.WeaponMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EquipmentService {

	@Autowired
	private ArmorDao armorDao;
	
	@Autowired
	private WeaponDao weaponDao;
	
	@Autowired
	private ArmorMapper armorMapper;
	
	@Autowired
	private WeaponMapper weaponMapper;
	
	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private EquippedItemDao equippedItemDao;
	
	@Autowired
	private EquippedItemMapper equippedItemMapper;
	
	
	@Autowired
	private EquipmentDao equipmentDao;

	@Transactional
	public List<ArmorDto> getAllArmors() {
        List<Armor> armors = armorDao.findAll();
        return armorMapper.toDtoList(armors);
	}
	
	@Transactional
	public List<WeaponDto> getAllWeapons() {
        List<Weapon> weapons = weaponDao.findAll();
        return weaponMapper.toDtoList(weapons);
	}
	
	
	
	@Transactional
	public EquippedItemDto equipCharacterWithSpecificGear(Long charId, Long gearId) {
		
		EquippedItem equippedItem;
		
		com.vinz.energy.EnergyForge.db.Character character = characterDao.findById(charId)
                .orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));
		
		
		Equipment equipment = equipmentDao.findById(gearId)
                .orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND));
		
		//REFACTORISER
		Optional<EquippedItem> firstHeadItemOptional = character.getEquippedItems().stream()
			    .filter(element -> EquipmentSlot.HEAD.equals(element.getSlot()))
			    .findFirst();

		if(firstHeadItemOptional.isPresent()) {
			equippedItem = firstHeadItemOptional.get();
		} else {
    		equippedItem = new EquippedItem();
        }
		//REFACTORISER
		

		equippedItem.setCharacter(character);
		equippedItem.setEquipment(equipment);
		equippedItem.setSlot(equipment.getSlot());
		
		equippedItemDao.save(equippedItem);
		
		return equippedItemMapper.toDto(equippedItem);
		
	}
}
