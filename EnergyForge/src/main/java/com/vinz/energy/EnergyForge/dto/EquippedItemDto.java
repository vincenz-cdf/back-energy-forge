package com.vinz.energy.EnergyForge.dto;

import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquippedItemDto {
    private Long id;
    private Long characterId;
    private Long equipmentId;
    private EquipmentSlot slot;
    
}
