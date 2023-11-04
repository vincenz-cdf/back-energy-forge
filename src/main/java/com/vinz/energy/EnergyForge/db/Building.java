package com.vinz.energy.EnergyForge.db;

import java.util.ArrayList;
import java.util.List;


import com.vinz.energy.EnergyForge.db.enums.BuildingType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String spritePath;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BuildingType type;
    
    @OneToMany(mappedBy = "building")
    private List<UserBuilding> userBuildings = new ArrayList<>();

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

	public List<UserBuilding> getUserBuildings() {
		return userBuildings;
	}

	public void setUserBuildings(List<UserBuilding> userBuildings) {
		this.userBuildings = userBuildings;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}
    
    
}
