package com.vinz.energy.EnergyForge.combat;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;

import jakarta.transaction.Transactional;

@SpringBootTest
class CombatSystemTests {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CharacterDao characterDao;
	
	Random random = new Random();
	
	@Transactional
	private Character initializeAll() {
		User user = new User();
		
		user.setEmail("a");
		user.setEnergy(0);
		user.setLogin("a");
		user.setPassword("sdqqqqqdsq");
		userDao.save(user);
		
		
    	Character character = new Character();
    	character.setArmor(0);
    	character.setAtk(0);
    	character.setHp(50);
    	character.setMaxHp(50);
    	character.setMaxArmor(0);
    	character.setLevel(1);
    	character.setCharacterType(CharacterType.HERO);
    	character.setName("test");
    	character.setSpritePath("assets/sprites/characters/mainchar.png");
    	character.setUser(user);
    	
    	characterDao.save(character);
    	
    	return character;

	}

	@Test
	void newTest() {
		
		System.out.println(initializeAll().getId());

	}
}
