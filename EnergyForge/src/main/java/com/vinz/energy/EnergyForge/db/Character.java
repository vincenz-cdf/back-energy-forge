package com.vinz.energy.EnergyForge.db;

import java.util.Set;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private Set<EquippedItem> equippedItems;
}


