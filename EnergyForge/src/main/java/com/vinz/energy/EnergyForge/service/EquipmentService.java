package com.vinz.energy.EnergyForge.service;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinz.energy.EnergyForge.dao.ArmorDao;
import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.dao.EquippedItemDao;
import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.dto.ArmorDto;
import com.vinz.energy.EnergyForge.dto.EquippedItemDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.ArmorMapper;
import com.vinz.energy.EnergyForge.mappers.EquippedItemMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EquipmentService {

	@Autowired
	private ArmorDao armorDao;
	
	@Autowired
	private ArmorMapper armorMapper;
	
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
	public void equipCharacterWithSpecificGear(Long charId, Long gearId) {
		
		EquippedItem equippedItem = new EquippedItem();
		
		com.vinz.energy.EnergyForge.db.Character character = characterDao.findById(charId)
                .orElseThrow(() -> new AppException("Unknown character", HttpStatus.NOT_FOUND));
		
		Equipment equipment = equipmentDao.findById(gearId)
                .orElseThrow(() -> new AppException("Unknown equipment", HttpStatus.NOT_FOUND));

		equippedItem.setCharacter(character);
		equippedItem.setEquipment(equipment);
		equippedItem.setSlot(equipment.getSlot());
		
		equippedItemDao.save(equippedItem);
		
		Hibernate.initialize(character.getEquippedItems().size());
		
	}
}
