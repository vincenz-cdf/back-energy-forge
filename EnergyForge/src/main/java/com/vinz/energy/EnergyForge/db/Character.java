package com.vinz.energy.EnergyForge.db;

import java.util.ArrayList;
import java.util.List;

import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.ToString;

@ToString
@Entity
@Table(name = "character")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer level;
    
    @Column(nullable = false)
    private Integer hp;
    
    @Column(nullable = false)
    private Integer armor;
    
    @Column(nullable = false)
    private Integer maxHp;
    
    @Column(nullable = false)
    private Integer maxArmor;

    @Column(nullable = false)
    private Integer atk;
    
    @Column(nullable = false)
    private String spritePath;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CharacterType characterType;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    
    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquippedItem> equippedItems = new ArrayList<EquippedItem>();
    
    @Column(nullable = false)
    private Integer morale;
    
    @Column(nullable = false)
    private Integer maxMorale;
    
    @Column(nullable = false)
    private Integer experiencePoint;
    
    @ManyToMany(mappedBy = "characters")
    private List<CombatInstance> combatInstances = new ArrayList<>();
    
    @Column(nullable = false)
    private Boolean recovering = false;
    
    @Column(nullable = false)
    private int healingStack = 0;
    
    @Column(nullable = false)
    private int healingPower = 0;
    
    @Column(nullable = false)
    private Boolean inCombat = false;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<EquippedItem> getEquippedItems() {
		return equippedItems;
	}

	public void setEquippedItems(List<EquippedItem> equippedItems) {
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

	public Integer getMorale() {
		return morale;
	}

	public void setMorale(Integer morale) {
		this.morale = morale;
	}

	public Integer getMaxMorale() {
		return maxMorale;
	}

	public void setMaxMorale(Integer maxMorale) {
		this.maxMorale = maxMorale;
	}

	public Integer getExperiencePoint() {
		return experiencePoint;
	}

	public void setExperiencePoint(Integer experiencePoint) {
		this.experiencePoint = experiencePoint;
	}

	public List<CombatInstance> getCombatInstances() {
		return combatInstances;
	}

	public void setCombatInstances(List<CombatInstance> combatInstances) {
		this.combatInstances = combatInstances;
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
 
	
}


