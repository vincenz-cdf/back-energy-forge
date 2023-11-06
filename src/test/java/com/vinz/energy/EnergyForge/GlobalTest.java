package com.vinz.energy.EnergyForge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;

import jakarta.transaction.Transactional;

@Service
public class GlobalTest {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CharacterDao characterDao;

	@Transactional
	public void initializeAll() {
		User user = new User();
		user.setEmail("a");
		user.setEnergy(0);
		user.setLogin("a");
		user.setPassword("sdqqqqqdsq");
		user.setCoin(0);
		userDao.save(user);

		User user2 = new User();
		user2.setEmail("b");
		user2.setEnergy(0);
		user2.setLogin("b");
		user2.setPassword("bbbbbqsf");
		user2.setCoin(0);
		userDao.save(user2);

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
		character.setExperiencePoint(0);
		character.setMorale(0);
		character.setMaxMorale(10);
		characterDao.save(character);

		Character character2 = new Character();
		character2.setArmor(0);
		character2.setAtk(0);
		character2.setHp(50);
		character2.setMaxHp(50);
		character2.setMaxArmor(0);
		character2.setLevel(1);
		character2.setCharacterType(CharacterType.HERO);
		character2.setName("test2");
		character2.setSpritePath("assets/sprites/characters/mainchar.png");
		character2.setUser(user2);
		character2.setExperiencePoint(0);
		character2.setMorale(0);
		character2.setMaxMorale(10);
		characterDao.save(character2);

	}
}
