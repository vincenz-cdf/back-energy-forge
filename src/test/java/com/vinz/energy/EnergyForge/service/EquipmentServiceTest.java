package com.vinz.energy.EnergyForge.service;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vinz.energy.EnergyForge.GlobalTest;
import com.vinz.energy.EnergyForge.dao.EquipmentDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.UserItemDao;
import com.vinz.energy.EnergyForge.db.UserItem;
import com.vinz.energy.EnergyForge.db.enums.ItemType;
import com.vinz.energy.EnergyForge.dto.equipments.ArmorDto;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EquipmentServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(EquipmentServiceTest.class);

	@Autowired
	private GlobalTest globalTest;

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private UserItemDao userItemDao;

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private UserDao userDao;

	@BeforeEach
	@Sql("/test-data.sql")
	@Transactional
	void setUp() {
	}

	@Disabled
	@Test
	@Transactional
	void testEquipmentProcessing() {

		globalTest.initializeAll();
		UserItem ui = new UserItem();
		ui.setConsumable(null);
		ui.setEquipment(equipmentDao.findById(1l).orElseThrow(() -> new RuntimeException("Equipment not found")));
		ui.setItemType(ItemType.EQUIPMENT);
		ui.setUser(userDao.findById(2l).orElseThrow(() -> new RuntimeException("User not found")));
		userItemDao.save(ui);

		List<ArmorDto> a = equipmentService.getOwnedArmors(2l);

		System.out.println("facts");
		for (ArmorDto armor : a) {
			logger.info(armor.toString());
		}
		

	}

}
