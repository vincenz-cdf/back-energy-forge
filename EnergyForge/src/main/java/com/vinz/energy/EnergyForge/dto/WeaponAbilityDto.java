package com.vinz.energy.EnergyForge.dto;


public class WeaponAbilityDto {
	private Integer id;

	private Long weaponId;
	private Integer equipmentAbilityId;
	
	private String equipmentAbilityName;
	private String equipmentAbilityDescription;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getWeaponId() {
		return weaponId;
	}
	public void setWeaponId(Long weaponId) {
		this.weaponId = weaponId;
	}
	public Integer getEquipmentAbilityId() {
		return equipmentAbilityId;
	}
	public void setEquipmentAbilityId(Integer equipmentAbilityId) {
		this.equipmentAbilityId = equipmentAbilityId;
	}
	public String getEquipmentAbilityName() {
		return equipmentAbilityName;
	}
	public void setEquipmentAbilityName(String equipmentAbilityName) {
		this.equipmentAbilityName = equipmentAbilityName;
	}
	public String getEquipmentAbilityDescription() {
		return equipmentAbilityDescription;
	}
	public void setEquipmentAbilityDescription(String equipmentAbilityDescription) {
		this.equipmentAbilityDescription = equipmentAbilityDescription;
	}

	
	
}
