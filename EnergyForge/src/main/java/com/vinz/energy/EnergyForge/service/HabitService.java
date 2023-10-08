package com.vinz.energy.EnergyForge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.HabitDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.Habit;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.dto.HabitDto;
import com.vinz.energy.EnergyForge.exception.AppException;

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
	
	public void updateHabit(Long habitId, HabitDto dto) {
		System.out.println(dto.toString());
		Habit habitDb = habitDao.findById(habitId).orElseThrow(() -> new AppException("Unknown habit", HttpStatus.NOT_FOUND));
		
		habitDb.setChecked(dto.getChecked());
		habitDb.setTitle(dto.getTitle());
		System.out.println(habitDb.toString());
		
		habitDao.save(habitDb);
		
	}
}
