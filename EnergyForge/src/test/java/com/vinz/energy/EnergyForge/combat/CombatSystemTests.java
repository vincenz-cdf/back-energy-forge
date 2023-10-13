package com.vinz.energy.EnergyForge.combat;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.service.Global;

@SpringBootTest
class CombatSystemTests {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CharacterDao characterDao;
	
	Random random = new Random();
	
	private void initializeAll() {
		User user = new User();
		
		user.setEmail("a");
		user.setEnergy(0);
		user.setLogin("a");
		user.setPassword("sdqqqqqdsq");
		userDao.save(user);
		
	}

	@Test
	void newTest() {
		initializeAll();
		
		System.out.println(characterDao.findAll());
	}
}
