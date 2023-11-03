package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.db.Consumable;
import com.vinz.energy.EnergyForge.db.UserItem;

import lombok.ToString;

@ToString
public class ConsumableDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Integer id;
    
    private String name;
    
    private String description;
    
    private Integer value;
    
    private String spritePath;
    
    private int healingAmount;
    
    private int healingPower;
    
    private List<UserItemDto> userItems = new ArrayList<UserItemDto>();
    
    public ConsumableDto() {}
    
    public ConsumableDto(Consumable c) {
    	id = c.getId();
    	name = c.getName();
    	spritePath = c.getSpritePath();
    	description = c.getDescription();
    	healingAmount = c.getHealingAmount();
    	healingPower = c.getHealingPower();
    	value = c.getValue();
    	
    	userItems = new ArrayList<UserItemDto>();
    	
    	for (UserItem ui : c.getUserItems()) {
    		userItems.add(new UserItemDto(ui));
    	}
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHealingAmount() {
		return healingAmount;
	}

	public void setHealingAmount(int healingAmount) {
		this.healingAmount = healingAmount;
	}

	public List<UserItemDto> getUserItems() {
		return userItems;
	}

	public void setUserItems(List<UserItemDto> userItems) {
		this.userItems = userItems;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getHealingPower() {
		return healingPower;
	}

	public void setHealingPower(int healingPower) {
		this.healingPower = healingPower;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
    
	
}
