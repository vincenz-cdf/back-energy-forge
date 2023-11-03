package com.vinz.energy.EnergyForge.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.ArmorDao;
import com.vinz.energy.EnergyForge.dao.BuildingDao;
import com.vinz.energy.EnergyForge.dao.ConsumableDao;
import com.vinz.energy.EnergyForge.dao.UserBuildingDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dao.WeaponDao;
import com.vinz.energy.EnergyForge.db.Building;
import com.vinz.energy.EnergyForge.db.Consumable;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.UserBuilding;
import com.vinz.energy.EnergyForge.db.equipments.Armor;
import com.vinz.energy.EnergyForge.db.equipments.Weapon;
import com.vinz.energy.EnergyForge.dto.BuildingDto;
import com.vinz.energy.EnergyForge.dto.ConsumableDto;
import com.vinz.energy.EnergyForge.dto.MarketItemPool;
import com.vinz.energy.EnergyForge.dto.equipments.ArmorDto;
import com.vinz.energy.EnergyForge.dto.equipments.WeaponDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.ArmorMapper;
import com.vinz.energy.EnergyForge.mappers.WeaponMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BuildingService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BuildingDao buildingDao;

	@Autowired
	private UserBuildingDao userBuildingDao;

	@Autowired
	private WeaponDao weaponDao;

	@Autowired
	private ArmorDao armorDao;

	@Autowired
	private ConsumableDao consumableDao;

	@Autowired
	private WeaponMapper weaponMapper;

	@Autowired
	private ArmorMapper armorMapper;

	private SecureRandom random = new SecureRandom();

	public void initializeFirstBuildingForUser(Long userId) {
		User user = userDao.findById(userId).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

		List<Integer> buildingIds = Arrays.asList(1, 2, 3); // Assuming the IDs are Long
		List<Building> buildings = buildingDao.findAllById(buildingIds);

		if (buildings.size() < buildingIds.size()) {
			throw new AppException("One or more buildings not found", HttpStatus.NOT_FOUND);
		}

		List<UserBuilding> userBuildings = buildings.stream().map(building -> {
			UserBuilding ub = new UserBuilding();
			ub.setBuilding(building);
			ub.setLevel(1);
			ub.setUser(user);
			ub.setMarketItemPool(building.getId() == 3 ? generateMarketPool(1) : null);
			return ub;
		}).collect(Collectors.toList());

		userBuildingDao.saveAll(userBuildings);
	}

	public List<BuildingDto> retrieveBuildingsForUser(Long userId) {
		List<UserBuilding> userBuildings = userBuildingDao.findByUser_Id(userId);
		return userBuildings.stream().map(BuildingDto::new).collect(Collectors.toList());
	}

	private MarketItemPool generateMarketPool(int marketLevel) {
		MarketItemPool marketItemPool = new MarketItemPool();
		List<Weapon> weapons = weaponDao.findAll();
		List<Armor> armors = armorDao.findAll();
		List<Consumable> consumables = consumableDao.findAll();

		// Check if equipments or consumables are empty to prevent random.nextInt from
		// throwing an exception
		if (weapons.isEmpty() || armors.isEmpty() || consumables.isEmpty()) {
			throw new IllegalStateException("Equipments or consumables cannot be empty.");
		}

		List<WeaponDto> selectedWeapons = new ArrayList<>();
		List<ArmorDto> selectedArmors = new ArrayList<>();
		List<ConsumableDto> selectedConsumables = new ArrayList<>();

		// Loop until the combined size of selected equipment and consumables is equal
		// to marketLevel + 3
		while (selectedWeapons.size() + selectedArmors.size() + selectedConsumables.size() < marketLevel + 3) {
			if (random.nextInt(100) < 60) {
				if (random.nextInt(100) < 50) {
					selectedWeapons.add(weaponMapper.toDto(weapons.get(random.nextInt(weapons.size()))));
				} else {
					selectedArmors.add(armorMapper.toDto(armors.get(random.nextInt(armors.size()))));
				}
			} else {
				selectedConsumables.add(new ConsumableDto(consumables.get(random.nextInt(consumables.size()))));
			}
		}

		marketItemPool.setConsumables(selectedConsumables);
		marketItemPool.setWeapons(selectedWeapons);
		marketItemPool.setArmors(selectedArmors);

		return marketItemPool;
	}
}
