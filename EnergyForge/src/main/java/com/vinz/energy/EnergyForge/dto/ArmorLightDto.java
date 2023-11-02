package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ArmorLightDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer armorValue;
	private String spritePath;
	private EquipmentSlot slot;
	
	public ArmorLightDto(Armor a) {
		id = a.getId();
		armorValue = a.getArmorValue();
		spritePath = a.getEquipment().getSpritePath();
		slot = a.getEquipment().getSlot();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getArmorValue() {
		return armorValue;
	}

	public void setArmorValue(Integer armorValue) {
		this.armorValue = armorValue;
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

	
}
