package com.vinz.energy.EnergyForge.db.equipments;

public enum EquipmentSlot {
	HEAD(false), 
	BODY(false), 
	LEFTHAND(true), 
	RIGHTHAND(true), 
	ZWEIHAND(true);
	//UTILITY
	
	private Boolean isWeapon;
	
	EquipmentSlot(Boolean isWeapon) {
		this.isWeapon = isWeapon;
	}
	
	public Boolean isWeapon() {
		return isWeapon;
	}
}
