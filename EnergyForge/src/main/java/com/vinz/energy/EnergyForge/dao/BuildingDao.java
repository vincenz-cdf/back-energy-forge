package com.vinz.energy.EnergyForge.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinz.energy.EnergyForge.db.Building;

public interface BuildingDao extends JpaRepository<Building, Integer> {

}
