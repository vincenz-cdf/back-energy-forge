package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.UserItem;
import com.vinz.energy.EnergyForge.db.enums.ItemType;

public class UserItemDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long equipmentId;
	private Long userId;
	private Integer consumableId;
	private ItemType itemType;
	
	public UserItemDto() {};
	
	public UserItemDto(UserItem ui) {
		id = ui.getId();
		userId = ui.getUser().getId();
		
		if(ui.getEquipment() != null) {
			equipmentId = ui.getEquipment().getId();
		}
		
		if(ui.getEquipment() != null) {
			consumableId = ui.getConsumable().getId();
		}
		
		itemType = ui.getItemType();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public Integer getConsumableId() {
		return consumableId;
	}

	public void setConsumableId(Integer consumableId) {
		this.consumableId = consumableId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
	
}
