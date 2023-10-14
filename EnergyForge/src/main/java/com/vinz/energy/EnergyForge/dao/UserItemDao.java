package com.vinz.energy.EnergyForge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinz.energy.EnergyForge.db.UserItem;

public interface UserItemDao extends JpaRepository<UserItem, Long> {

	
	List<UserItem> findByEquipmentId(Long equipmentId);
	
	List<UserItem> findByUserId(Long userId);
}
