package com.vinz.energy.EnergyForge.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDto {
	private Long id;
	private String name;
	private Integer value;
	private String spritePath;
	
	private EquipmentSlot slot;
	
	private List<EquippedItemDto> equippedItems = new ArrayList<EquippedItemDto>();

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

	
}
