package com.vinz.energy.EnergyForge.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinz.energy.EnergyForge.db.equipments.Weapon;

public interface WeaponDao extends JpaRepository<Weapon, Long> {

}
