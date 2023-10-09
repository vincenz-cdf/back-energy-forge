package com.vinz.energy.EnergyForge.service.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vinz.energy.EnergyForge.dao.HabitDao;
import com.vinz.energy.EnergyForge.db.Habit;

@Component
public class ScheduledTasks {

    @Autowired
    private HabitDao habitDao;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetHabitCheckedStatus() {
    	System.out.println("CRON ACTION");
        List<Habit> checkedHabits = habitDao.findByChecked(true);
        checkedHabits.forEach(habit -> habit.setChecked(false));
        habitDao.saveAll(checkedHabits);
    }
}
