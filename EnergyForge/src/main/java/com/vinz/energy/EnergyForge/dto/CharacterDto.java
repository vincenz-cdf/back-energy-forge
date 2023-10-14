package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.dto.equipments.EquippedItemDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CharacterDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer level;
	private Integer hp;
	private Integer armor;
	private Integer maxHp;
	private Integer maxArmor;
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

	public Integer getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(Integer maxHp) {
		this.maxHp = maxHp;
	}

	public Integer getMaxArmor() {
		return maxArmor;
	}

	public void setMaxArmor(Integer maxArmor) {
		this.maxArmor = maxArmor;
	}
	
	
}
