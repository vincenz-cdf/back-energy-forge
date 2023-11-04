package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.UserBuilding;
import com.vinz.energy.EnergyForge.db.enums.BuildingType;

public class BuildingDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;
	private BuildingType type;
	private String spritePath;
	private int level;
	private MarketItemPool marketItemPool;
	
	public BuildingDto() {
	}
	
	public BuildingDto(UserBuilding b) {
		id = b.getBuilding().getId();
		name = b.getBuilding().getName();
		description = b.getBuilding().getDescription();
		type = b.getBuilding().getType();
		level = b.getLevel();	
		marketItemPool = b.getMarketItemPool();
		spritePath = b.getBuilding().getSpritePath();
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

	public BuildingType getType() {
		return type;
	}

	public void setType(BuildingType type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public MarketItemPool getMarketItemPool() {
		return marketItemPool;
	}

	public void setMarketItemPool(MarketItemPool marketItemPool) {
		this.marketItemPool = marketItemPool;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}
	
	
}
