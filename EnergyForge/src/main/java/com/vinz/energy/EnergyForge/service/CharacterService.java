package com.vinz.energy.EnergyForge.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.dto.CharacterDto;
import com.vinz.energy.EnergyForge.mappers.CharacterMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CharacterService {

    private final UserDao userDao;
    private final CharacterDao characterDao;
    private final CharacterMapper characterMapper;

	public List<CharacterDto> showCharacters(Long userId) {
	    List<com.vinz.energy.EnergyForge.db.Character> chars = characterDao.findByUserIdOrderByLevelAsc(userId);
	    
	    return characterMapper.toCharacterDtos(chars);
	}
}
