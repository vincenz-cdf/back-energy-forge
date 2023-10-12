package com.vinz.energy.EnergyForge.db.equipments;

import java.util.Set;

import com.vinz.energy.EnergyForge.db.equipments.abilities.WeaponAbility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
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
    private Set<WeaponAbility> weaponAbilities;
    
    
}
