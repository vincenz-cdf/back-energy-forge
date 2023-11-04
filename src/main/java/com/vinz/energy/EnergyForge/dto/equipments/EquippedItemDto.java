package com.vinz.energy.EnergyForge.dto.equipments;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.equipments.EquipmentSlot;
import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;

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
    
    public EquippedItemDto(EquippedItem ei) {
    	id = ei.getId();
    	characterId = ei.getCharacter().getId();
    	equipmentId = ei.getEquipment().getId();
    	equipmentName = ei.getEquipment().getName();
    	equipmentValue = ei.getEquipment().getValue();
    	equipmentSpritePath = ei.getEquipment().getSpritePath();
    	slot = ei.getSlot();
    }
}