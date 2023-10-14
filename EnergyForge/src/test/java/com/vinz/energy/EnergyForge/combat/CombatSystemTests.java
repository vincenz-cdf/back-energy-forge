package com.vinz.energy.EnergyForge.combat;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.db.enums.Effect;
import com.vinz.energy.EnergyForge.dto.AttackActions;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.dto.CumulatedTeamStats;
import com.vinz.energy.EnergyForge.dto.equipments.EquippedItemDto;
import com.vinz.energy.EnergyForge.dto.equipments.WeaponDto;
import com.vinz.energy.EnergyForge.dto.equipments.abilities.WeaponAbilityDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.CharacterMapper;
import com.vinz.energy.EnergyForge.mappers.WeaponMapper;
import com.vinz.energy.EnergyForge.service.EquipmentService;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
@SpringBootTest
class CombatSystemTests {

	private static final Logger logger = LoggerFactory.getLogger(CombatSystemTests.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private CharacterDao characterDao;

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private CharacterMapper characterMapper;

	@Autowired
	private WeaponDao weaponDao;

	@Autowired
	private WeaponMapper weaponMapper;

	Random random = new Random();

	@Transactional
	private void initializeAll() {
		User user = new User();
		user.setEmail("a");
		user.setEnergy(0);
		user.setLogin("a");
		user.setPassword("sdqqqqqdsq");
		userDao.save(user);

		User user2 = new User();
		user2.setEmail("b");
		user2.setEnergy(0);
		user2.setLogin("b");
		user2.setPassword("bbbbbqsf");
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
		characterDao.save(character2);

	}

	@Sql("/test-data.sql")
	@Test
	@Transactional
	void newTest() {

		try {
			initializeAll();

			equipmentService.equipCharacterWithSpecificGear(1l, 1l);
			equipmentService.equipCharacterWithSpecificGear(1l, 4l);

			equipmentService.equipCharacterWithSpecificGear(2l, 2l);
			equipmentService.equipCharacterWithSpecificGear(2l, 3l);

			beginFight(calculateTeamStats(Arrays.asList(1l), Arrays.asList(2l)));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private CumulatedTeamStats calculateTeamStats(List<Long> yourTeam, List<Long> enemyTeam) {
		CumulatedTeamStats allTeamStats = new CumulatedTeamStats();

		for (Long id : yourTeam) {
			CharacterDto dto = characterMapper.toDto(characterDao.findById(id)
					.orElseThrow(() -> new AppException("Unknown char", HttpStatus.NOT_FOUND)));

			allTeamStats.setTotalHp(allTeamStats.getTotalHp() + dto.getHp());
			allTeamStats.setTotalArmor(allTeamStats.getTotalArmor() + dto.getArmor());
			allTeamStats.getYourDto().add(dto);
		}

		for (Long id : enemyTeam) {
			CharacterDto dto = characterMapper.toDto(characterDao.findById(id)
					.orElseThrow(() -> new AppException("Unknown char", HttpStatus.NOT_FOUND)));

			allTeamStats.setEnemyHp(allTeamStats.getEnemyHp() + dto.getHp());
			allTeamStats.setEnemyArmor(allTeamStats.getEnemyArmor() + dto.getArmor());
			allTeamStats.getEnemyDto().add(dto);
		}

		return allTeamStats;
	}

	private void beginFight(CumulatedTeamStats allTeamStats) {

		List<WeaponDto> yourWeapon = new ArrayList<WeaponDto>();
		List<WeaponDto> enemyWeapon = new ArrayList<WeaponDto>();

		for (CharacterDto c : allTeamStats.getYourDto()) {

			for (EquippedItemDto item : c.getEquippedItems()) {
				if (item.getSlot().isWeapon()) {
					yourWeapon.add(weaponMapper.toDto(weaponDao.findByEquipmentId(item.getEquipmentId())
							.orElseThrow(() -> new AppException("Unknown char", HttpStatus.NOT_FOUND))));
				}
			}

		}

		for (CharacterDto c : allTeamStats.getEnemyDto()) {

			for (EquippedItemDto item : c.getEquippedItems()) {
				if (item.getSlot().isWeapon()) {
					enemyWeapon.add(weaponMapper.toDto(weaponDao.findByEquipmentId(item.getEquipmentId())
							.orElseThrow(() -> new AppException("Unknown char", HttpStatus.NOT_FOUND))));
				}
			}

		}

		while (allTeamStats.getTotalHp() > 0 && allTeamStats.getEnemyHp() > 0) {

			for (WeaponDto w : enemyWeapon) {

				AttackActions aa = weaponSpecialization(w);

				for (int i = 0; i < aa.getAtkCount(); i++) {
					if (allTeamStats.getTotalArmor() > 0) {
						Integer newArmorValue = allTeamStats.getTotalArmor() - aa.getAtkAmount();

						if (newArmorValue <= 0) {
							allTeamStats.setTotalArmor(0);
							allTeamStats.setTotalHp(allTeamStats.getTotalHp() + newArmorValue);
						} else {
							allTeamStats.setTotalArmor(newArmorValue);
						}
					} else {
						allTeamStats.setTotalHp(allTeamStats.getTotalHp() - aa.getAtkAmount());
					}
				}

			}

			for (WeaponDto w : yourWeapon) {

				AttackActions aa = weaponSpecialization(w);

				for (int i = 0; i < aa.getAtkCount(); i++) {
					if (allTeamStats.getEnemyArmor() > 0) {
						Integer newArmorValue = allTeamStats.getEnemyArmor() - aa.getAtkAmount();
						if (newArmorValue <= 0) {
							allTeamStats.setEnemyArmor(0);
							allTeamStats.setEnemyHp(allTeamStats.getEnemyHp() + newArmorValue);
						} else {
							allTeamStats.setEnemyArmor(newArmorValue);
						}
					} else {
						allTeamStats.setEnemyHp(allTeamStats.getEnemyHp() - aa.getAtkAmount());
					}
				}

			}

		}

		System.out.println(allTeamStats.getTotalHp());
		System.out.println(allTeamStats.getEnemyHp());
	}

	private AttackActions weaponSpecialization(WeaponDto weapon) {

		AttackActions aa = new AttackActions();
		aa.setAtkAmount(weapon.getAtkValue());

		for (WeaponAbilityDto a : weapon.getWeaponAbilities()) {
			
			System.out.println(a);
			
			if (a.getEquipmentAbilityEffect() == Effect.SWINGSPLIT) {
				if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue()+30) {
					aa.setAtkCount(aa.getAtkCount() + 1);
					if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue()) {
						aa.setAtkAmount(aa.getAtkAmount() * 2);
					} else {
						return aa;
					}
				}
			}

			if (a.getEquipmentAbilityEffect() == Effect.RIPOSTE) {
				if (random.nextInt(100) + 1 <= a.getEquipmentAbilityValue()+30) {
					aa.setAtkAmount(aa.getAtkAmount() * 2);
				}
			}
		}

		return aa;

	}
}
