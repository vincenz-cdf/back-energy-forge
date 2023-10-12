package com.vinz.energy.EnergyForge.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinz.energy.EnergyForge.db.equipments.Equipment;

public interface EquipmentDao extends JpaRepository<Equipment, Long>{

}
