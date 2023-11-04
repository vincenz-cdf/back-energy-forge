package com.vinz.energy.EnergyForge.db.equipments;

import java.util.ArrayList;
import java.util.List;


import com.vinz.energy.EnergyForge.db.equipments.abilities.WeaponAbility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "weapon")
public class Weapon {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    @Column(nullable = false)
    private Integer atkValue;
    @Column(nullable = false)
    private Boolean isMelee;
    
    @OneToMany(mappedBy = "weapon")
    private List<WeaponAbility> weaponAbilities = new ArrayList<WeaponAbility>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Integer getAtkValue() {
		return atkValue;
	}

	public void setAtkValue(Integer atkValue) {
		this.atkValue = atkValue;
	}

	public Boolean getIsMelee() {
		return isMelee;
	}

	public void setIsMelee(Boolean isMelee) {
		this.isMelee = isMelee;
	}

	public List<WeaponAbility> getWeaponAbilities() {
		return weaponAbilities;
	}

	public void setWeaponAbilities(List<WeaponAbility> weaponAbilities) {
		this.weaponAbilities = weaponAbilities;
	}

    
    
}
