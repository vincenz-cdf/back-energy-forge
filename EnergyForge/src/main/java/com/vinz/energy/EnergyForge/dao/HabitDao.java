package com.vinz.energy.EnergyForge.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinz.energy.EnergyForge.db.Habit;

public interface HabitDao extends JpaRepository<Habit, Long> {
    @Query("SELECT h FROM Habit h WHERE h.user.id = :userId ORDER BY h.id ASC")
    List<Habit> findByUserIdOrderByIdAsc(@Param("userId") Long userId);
    
    List<Habit> findByChecked(boolean checked);
}

