package com.vinz.energy.EnergyForge.db.equipments.abilities;


import com.vinz.energy.EnergyForge.db.equipments.Armor;

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
@Table(name = "armor_ability")
public class ArmorAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "armor_id", referencedColumnName = "equipment_id")
    private Armor armor;

    @ManyToOne
    @JoinColumn(name = "ability_id", referencedColumnName = "id")
    private EquipmentAbility ability;
}
