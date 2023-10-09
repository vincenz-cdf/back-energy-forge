package com.vinz.energy.EnergyForge.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.HabitDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.Habit;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.dto.HabitDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.HabitMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HabitService {
	
    private final UserDao userDao;
    private final HabitDao habitDao;
    private final HabitMapper habitMapper;

	public void createEmptyHabit(Long userId) {
		Habit habit = new Habit();
		habit.setChecked(false);
		habit.setTitle("New Task");
		habit.setUser(userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
		
		habitDao.save(habit);
	}
	
	public List<HabitDto> showHabits(Long userId) {
	    List<Habit> habits = habitDao.findByUserIdOrderByIdAsc(userId);
	    
	    return habitMapper.toHabitDtos(habits);
	}
	
	public void updateHabit(Long habitId, HabitDto dto) {
		Habit habitDb = habitDao.findById(habitId).orElseThrow(() -> new AppException("Unknown habit", HttpStatus.NOT_FOUND));
		
		if(!habitDb.getChecked() && dto.getChecked()) {
			incrementEnergy(habitDb.getUser().getId());
		}
		
		habitDb.setChecked(dto.getChecked());
		habitDb.setTitle(dto.getTitle());
		
		habitDao.save(habitDb);
		
	}
	
	private void incrementEnergy(Long userId) {
		User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Integer currentEnergy = user.getEnergy();
		user.setEnergy(currentEnergy+2);
		
		userDao.save(user);
		
	}
}
