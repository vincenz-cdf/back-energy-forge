package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;
import com.vinz.energy.EnergyForge.dto.equipments.EquippedItemDto;

import lombok.ToString;

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
	private Integer maxMorale;
	private Integer morale;
	private Integer experiencePoint;
	private Boolean recovering;
	private int healingStack;
	private int healingPower;
	private Boolean inCombat = false;
	private Boolean awaiting = false;
	
	private List<EquippedItemDto> equippedItems = new ArrayList<EquippedItemDto>();
	
	public CharacterDto() {
	}
	
	public CharacterDto(com.vinz.energy.EnergyForge.db.Character c) {
		id = c.getId();
		name = c.getName();
		level = c.getLevel();
		hp = c.getHp();
		armor = c.getArmor();
		maxHp = c.getMaxHp();
		maxArmor = c.getMaxArmor();
		atk = c.getAtk();
		spritePath = c.getSpritePath();
		characterType = c.getCharacterType();
		userId = c.getUser().getId();
		maxMorale = c.getMaxMorale();
		morale = c.getMorale();
		experiencePoint = c.getExperiencePoint();
		recovering = c.getRecovering();
		healingStack = c.getHealingStack();
		healingPower = c.getHealingPower();
		inCombat = c.getInCombat();
		awaiting = c.getAwaiting();
		equippedItems = new ArrayList<EquippedItemDto>();
		
		for (EquippedItem ei : c.getEquippedItems()) {
			equippedItems.add(new EquippedItemDto(ei));
		}
			
		
	}

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

	public Integer getMaxMorale() {
		return maxMorale;
	}

	public void setMaxMorale(Integer maxMorale) {
		this.maxMorale = maxMorale;
	}

	public Integer getMorale() {
		return morale;
	}

	public void setMorale(Integer morale) {
		this.morale = morale;
	}

	public Integer getExperiencePoint() {
		return experiencePoint;
	}

	public void setExperiencePoint(Integer experiencePoint) {
		this.experiencePoint = experiencePoint;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getRecovering() {
		return recovering;
	}

	public void setRecovering(Boolean recovering) {
		this.recovering = recovering;
	}

	public int getHealingStack() {
		return healingStack;
	}

	public void setHealingStack(int healingStack) {
		this.healingStack = healingStack;
	}

	public Boolean getInCombat() {
		return inCombat;
	}

	public void setInCombat(Boolean inCombat) {
		this.inCombat = inCombat;
	}

	public int getHealingPower() {
		return healingPower;
	}

	public void setHealingPower(int healingPower) {
		this.healingPower = healingPower;
	}

	public Boolean getAwaiting() {
		return awaiting;
	}

	public void setAwaiting(Boolean awaiting) {
		this.awaiting = awaiting;
	}

}
