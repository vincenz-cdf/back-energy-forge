package com.vinz.energy.EnergyForge.dao;

import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface CharacterDao extends JpaRepository<Character, Long> {
    @Query("SELECT c FROM Character c WHERE c.user.id = :userId ORDER BY c.level ASC LIMIT 3")
    List<Character> findByUserIdOrderByLevelAsc(@Param("userId") Long userId);
    
    List<Character> findByCharacterType(CharacterType characterType);
    
    List<Character> findByUserId(Long userId);
}