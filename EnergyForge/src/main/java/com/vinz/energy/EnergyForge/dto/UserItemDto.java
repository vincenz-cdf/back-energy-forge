package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

public class UserItemDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long Id;
	private Long equipmentId;
	private Long userId;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
