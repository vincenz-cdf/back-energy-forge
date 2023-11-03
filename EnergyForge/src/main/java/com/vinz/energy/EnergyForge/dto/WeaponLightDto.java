package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.Weapon;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WeaponLightDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private boolean isMelee;
	private Integer atkValue;
	private String spritePath;
	private EquipmentSlot slot;
	private Integer value;
	
	public WeaponLightDto(Weapon w) {
		id = w.getId();
		atkValue = w.getAtkValue();
		spritePath = w.getEquipment().getSpritePath();
		slot = w.getEquipment().getSlot();
		value = w.getEquipment().getValue();
		name = w.getEquipment().getName();
		isMelee = w.getIsMelee();
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
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

	public boolean isMelee() {
		return isMelee;
	}

	public void setMelee(boolean isMelee) {
		this.isMelee = isMelee;
	}
	
}
