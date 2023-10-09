package com.vinz.energy.EnergyForge.dao;

import com.vinz.energy.EnergyForge.db.Character;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface CharacterDao extends JpaRepository<Character, Long> {
    @Query("SELECT c FROM Character c WHERE c.user.id = :userId ORDER BY c.level ASC")
    List<Character> findByUserIdOrderByLevelAsc(@Param("userId") Long userId);
}