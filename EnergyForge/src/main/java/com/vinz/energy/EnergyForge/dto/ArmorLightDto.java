package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ArmorLightDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer armorValue;
	private String spritePath;
	private EquipmentSlot slot;
	private int value;
	
	public ArmorLightDto(Armor a) {
		id = a.getId();
		armorValue = a.getArmorValue();
		spritePath = a.getEquipment().getSpritePath();
		slot = a.getEquipment().getSlot();
		value = a.getEquipment().getValue();
		name = a.getEquipment().getName();
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
