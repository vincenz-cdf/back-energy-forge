package com.vinz.energy.EnergyForge.db.equipments;

import java.util.Set;

import com.vinz.energy.EnergyForge.db.equipments.abilities.ArmorAbility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "armor")
public class Armor {
    @Id
    @OneToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    @Column(nullable = false)
    private Integer armorValue;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "slot", nullable = false)
    private EquipmentSlot slot;

    @OneToMany(mappedBy = "armor")
    private Set<ArmorAbility> armorAbilities;
}
