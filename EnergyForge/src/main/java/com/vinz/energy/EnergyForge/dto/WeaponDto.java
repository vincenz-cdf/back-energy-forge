package com.vinz.energy.EnergyForge.dto;

import java.util.Set;


public class WeaponDto {
    private Long id;
    private EquipmentDto equipment;
    private Integer atkValue;
    private Boolean isMelee;
    private Set<WeaponAbilityDto> weaponAbilities;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EquipmentDto getEquipment() {
		return equipment;
	}
	public void setEquipment(EquipmentDto equipment) {
		this.equipment = equipment;
	}
	public Integer getAtkValue() {
		return atkValue;
	}
	public void setAtkValue(Integer atkValue) {
		this.atkValue = atkValue;
	}
	public Boolean getIsMelee() {
		return isMelee;
	}
	public void setIsMelee(Boolean isMelee) {
		this.isMelee = isMelee;
	}
	public Set<WeaponAbilityDto> getWeaponAbilities() {
		return weaponAbilities;
	}
	public void setWeaponAbilities(Set<WeaponAbilityDto> weaponAbilities) {
		this.weaponAbilities = weaponAbilities;
	}
    
    
}
