package com.vinz.energy.EnergyForge.db;


import com.vinz.energy.EnergyForge.config.MarketItemPoolConverter;
import com.vinz.energy.EnergyForge.dto.MarketItemPool;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "user_building")
public class UserBuilding {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = true)
    private Building building;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    

    @Column(nullable = false)
    private Integer level;
    
    
    @Column(nullable = true, columnDefinition = "TEXT")
    @Convert(converter = MarketItemPoolConverter.class)
    private MarketItemPool marketItemPool;
    
}
