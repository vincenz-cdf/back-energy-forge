package com.vinz.energy.EnergyForge.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinz.energy.EnergyForge.db.Habit;

public interface HabitDao extends JpaRepository<Habit, Long> {
    List<Habit> findByUserId(Long userId);
}

