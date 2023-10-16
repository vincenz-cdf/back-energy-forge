package com.vinz.energy.EnergyForge.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CombatIndexes {

	private int characterIndex;
	private int weaponIndex;
	private Integer shieldIndex = null;
	
	public int getCharacterIndex() {
		return characterIndex;
	}
	public void setCharacterIndex(int characterIndex) {
		this.characterIndex = characterIndex;
	}
	public int getWeaponIndex() {
		return weaponIndex;
	}
	public void setWeaponIndex(int weaponIndex) {
		this.weaponIndex = weaponIndex;
	}
	public Integer getShieldIndex() {
		return shieldIndex;
	}
	public void setShieldIndex(Integer shieldIndex) {
		this.shieldIndex = shieldIndex;
	}
	
	
}
