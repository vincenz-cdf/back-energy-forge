package com.vinz.energy.EnergyForge.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class WeaponDto {
    private Integer id;
    private EquipmentDto equipment;
    private Integer atkValue;
    private Boolean isMelee;
    private List<WeaponAbilityDto> weaponAbilities = new ArrayList<WeaponAbilityDto>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public List<WeaponAbilityDto> getWeaponAbilities() {
		return weaponAbilities;
	}
	public void setWeaponAbilities(List<WeaponAbilityDto> weaponAbilities) {
		this.weaponAbilities = weaponAbilities;
	}
	
    
}
