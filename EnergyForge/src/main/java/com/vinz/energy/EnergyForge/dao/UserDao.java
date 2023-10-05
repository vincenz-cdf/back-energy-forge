package com.vinz.energy.EnergyForge.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinz.energy.EnergyForge.db.User;

import java.util.Optional;


public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
}
