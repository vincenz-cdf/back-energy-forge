package com.vinz.energy.EnergyForge.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinz.energy.EnergyForge.db.UserItem;
import com.vinz.energy.EnergyForge.db.enums.ItemType;

public interface UserItemDao extends JpaRepository<UserItem, Long> {

	
	List<UserItem> findByEquipmentId(Long equipmentId);
	
	List<UserItem> findByUserId(Long userId);
	
	List<UserItem> findByUserIdAndItemType(Long userId, ItemType itemType);
	
	List<UserItem> findByConsumableIdAndUserId(Integer id, Long userId);
	
    @Modifying
    @Query("DELETE FROM UserItem ui WHERE ui.id IN :ids")
    void deleteByIdIn(@Param("ids") Set<Long> ids);
}
