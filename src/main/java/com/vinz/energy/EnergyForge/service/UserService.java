package com.vinz.energy.EnergyForge.service;

import java.nio.CharBuffer;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinz.energy.EnergyForge.dao.CharacterDao;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.Character;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.db.enums.CharacterType;
import com.vinz.energy.EnergyForge.dto.UserDto;
import com.vinz.energy.EnergyForge.dto.auth.CredentialsDto;
import com.vinz.energy.EnergyForge.dto.auth.SignUpDto;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.mappers.UserMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	@Autowired
    private UserDao userDao;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
    private UserMapper userMapper;
	
	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private ExpeditionService expeditionService;
	
	@Autowired
	private BuildingService buildingService;
	
	Random random = new Random();

	@Transactional
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userDao.findByLogin(credentialsDto.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }
    
	@Transactional
    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userDao.findByLogin(userDto.login());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        User savedUser = userDao.save(user);
        
        createFirstCharacterForNewAccount(savedUser.getId());
        createFirstCharacterForNewAccount(savedUser.getId());
        
        expeditionService.createContractCombatInstance(savedUser.getId());
        expeditionService.createContractCombatInstance(savedUser.getId());
        
        buildingService.initializeFirstBuildingForUser(savedUser.getId());

        return userMapper.toDto(savedUser);
    }
    
    public UserDto findByLogin(String login) {
        User user = userDao.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toDto(user);
    }
    
    private void createFirstCharacterForNewAccount(Long userId) {
    	Character character = new Character();
    	character.setArmor(0);
    	character.setAtk(8);
    	character.setHp(50);
    	character.setMaxHp(50);
    	character.setMaxArmor(0);
    	character.setLevel(1);
    	character.setCharacterType(CharacterType.HERO);
    	character.setName(Global.defaultCharNames[random.nextInt(Global.defaultCharNames.length)]);
    	character.setSpritePath("assets/sprites/characters/mainchar.png");
    	character.setUser(userDao.findById(userId)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND)));
    	character.setExperiencePoint(0);
    	character.setMorale(10);
    	character.setMaxMorale(10);
    	
    	characterDao.save(character);
    }

}
