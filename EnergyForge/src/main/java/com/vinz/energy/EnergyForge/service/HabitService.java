package com.vinz.energy.EnergyForge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.HabitDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.Habit;
import com.vinz.energy.EnergyForge.dto.HabitDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HabitService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HabitDao habitDao;
	
    @Autowired
    private DataUtil dataUtil;

	public void createEmptyHabit(Long userId) {
		Habit habit = new Habit();
		habit.setChecked(false);
		habit.setTitle("New Task");
		habit.setUser(userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
		
		habitDao.save(habit);
	}
	
	public List<HabitDto> showHabits(Long userId) {
		List<Habit> habits = habitDao.findByUserId(userId);
		
		return dataUtil.convertToDtoList(habits);
	}
}
