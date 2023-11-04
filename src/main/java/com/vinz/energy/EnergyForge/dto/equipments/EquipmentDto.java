package com.vinz.energy.EnergyForge.dto.equipments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.db.equipments.Equipment;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.dto.UserItemDto;

public class EquipmentDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer value;
	private String spritePath;
	
	private EquipmentSlot slot;
	
	private List<EquippedItemDto> equippedItems = new ArrayList<EquippedItemDto>();
	private List<UserItemDto> userItems = new ArrayList<UserItemDto>();
	
	public EquipmentDto() {	
	}
	
	public EquipmentDto(Equipment equipment) {
		id = equipment.getId();
		name = equipment.getName();
		value = equipment.getValue();
		spritePath = equipment.getSpritePath();
		slot = equipment.getSlot();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}

	public EquipmentSlot getSlot() {
		return slot;
	}

	public void setSlot(EquipmentSlot slot) {
		this.slot = slot;
	}

	public List<EquippedItemDto> getEquippedItems() {
		return equippedItems;
	}

	public void setEquippedItems(List<EquippedItemDto> equippedItems) {
		this.equippedItems = equippedItems;
	}

	public List<UserItemDto> getUserItems() {
		return userItems;
	}

	public void setUserItems(List<UserItemDto> userItems) {
		this.userItems = userItems;
	}
	
	
}
