package com.vinz.energy.EnergyForge.db.equipments.abilities;

import com.vinz.energy.EnergyForge.db.equipments.Weapon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "weapon_ability")
public class WeaponAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "weapon_id", referencedColumnName = "equipment_id")
    private Weapon weapon;

    @ManyToOne
    @JoinColumn(name = "ability_id", referencedColumnName = "id")
    private EquipmentAbility ability;
}
