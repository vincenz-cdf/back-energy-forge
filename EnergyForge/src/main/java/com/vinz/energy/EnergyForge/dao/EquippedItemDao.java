package com.vinz.energy.EnergyForge.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinz.energy.EnergyForge.db.equipments.EquippedItem;

public interface EquippedItemDao extends JpaRepository<EquippedItem, Long> {

	Optional<EquippedItem> findByEquipmentId(Long equipmentId);
}
