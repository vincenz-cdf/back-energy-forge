package com.vinz.energy.EnergyForge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinz.energy.EnergyForge.db.UserBuilding;

public interface UserBuildingDao extends JpaRepository<UserBuilding, Long> {

	List<UserBuilding> findByUser_Id(Long userId);
	
	List<UserBuilding> findByBuilding_Id(int buildingId);
	
	UserBuilding findByUser_IdAndBuilding_Id(Long userId, Integer buildingId);

}
