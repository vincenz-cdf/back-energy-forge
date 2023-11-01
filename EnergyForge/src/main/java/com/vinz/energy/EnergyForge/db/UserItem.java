package com.vinz.energy.EnergyForge.db;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.enums.ItemType;
import com.vinz.energy.EnergyForge.db.equipments.Equipment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "user_item")
public class UserItem implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = true)
    private Equipment equipment;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "consumable_id", nullable = true)
    private Consumable consumable;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType itemType;
    
}
