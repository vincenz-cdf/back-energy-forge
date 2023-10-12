package com.vinz.energy.EnergyForge.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CharacterDto {

	private Long id;
	private String name;
	private Integer level;
	private Integer hp;
	private Integer armor;
	private Integer atk;
	private String spritePath;
	private CharacterType characterType;
	private Long userId;
	
	private List<EquippedItemDto> equippedItems = new ArrayList<EquippedItemDto>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Integer getArmor() {
		return armor;
	}

	public void setArmor(Integer armor) {
		this.armor = armor;
	}

	public Integer getAtk() {
		return atk;
	}

	public void setAtk(Integer atk) {
		this.atk = atk;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}

	public CharacterType getCharacterType() {
		return characterType;
	}

	public void setCharacterType(CharacterType characterType) {
		this.characterType = characterType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<EquippedItemDto> getEquippedItems() {
		return equippedItems;
	}

	public void setEquippedItems(List<EquippedItemDto> equippedItems) {
		this.equippedItems = equippedItems;
	}
	
	
}
