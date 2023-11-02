package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.Weapon;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WeaponLightDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer atkValue;
	private String spritePath;
	private EquipmentSlot slot;
	
	public WeaponLightDto(Weapon w) {
		id = w.getId();
		atkValue = w.getAtkValue();
		spritePath = w.getEquipment().getSpritePath();
		slot = w.getEquipment().getSlot();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAtkValue() {
		return atkValue;
	}

	public void setAtkValue(Integer atkValue) {
		this.atkValue = atkValue;
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
