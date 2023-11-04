package com.vinz.energy.EnergyForge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;

public interface EquippedItemDao extends JpaRepository<EquippedItem, Long> {
	
	List<EquippedItem> findByCharacterId(Long characterId);
	
    @Modifying
    @Query("DELETE FROM EquippedItem ei WHERE ei.character IN :characters")
    void deleteByCharacterIn(@Param("characters") List<com.vinz.energy.EnergyForge.db.Character> characters);
}
