package com.vinz.energy.EnergyForge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.dto.UserDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.UserMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileService {
	
	@Autowired
    private UserDao userDao;

	@Autowired
    private UserMapper userMapper;
	
    public UserDto findById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toDto(user);
    }
    
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userPrincipal = (UserDto) authentication.getPrincipal();
        return userPrincipal.getId();
    }
    
	@Transactional
	public void manageEnergyCost(Long userId, int cost) {
		
		User user = userDao.findById(userId)
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		
		if(user.getEnergy() < cost) {
			throw new AppException("Not enough energy", HttpStatus.FORBIDDEN);
		} else {
			user.setEnergy(user.getEnergy() - cost);
			userDao.save(user);
		}
		
	}

}