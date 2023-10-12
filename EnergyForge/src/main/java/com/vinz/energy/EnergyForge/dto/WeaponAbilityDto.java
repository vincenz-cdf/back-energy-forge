package com.vinz.energy.EnergyForge.dto;


public class WeaponAbilityDto {
	private Long id;
	private WeaponDto weapon;
	private EquipmentAbilityDto ability;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public WeaponDto getWeapon() {
		return weapon;
	}
	public void setWeapon(WeaponDto weapon) {
		this.weapon = weapon;
	}
	public EquipmentAbilityDto getAbility() {
		return ability;
	}
	public void setAbility(EquipmentAbilityDto ability) {
		this.ability = ability;
	}
	
	
}
