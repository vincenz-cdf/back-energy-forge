package com.vinz.energy.EnergyForge.dto.equipments;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquippedItemDto implements Serializable{
    private static final long serialVersionUID = 1L;
	private Long id;
    private Long characterId;
    private Long equipmentId;
    private String equipmentName;
    private Integer equipmentValue;
    private String equipmentSpritePath;
    private EquipmentSlot slot;
    
}