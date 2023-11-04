package com.vinz.energy.EnergyForge.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vinz.energy.EnergyForge.config.UserAuthProvider;
import com.vinz.energy.EnergyForge.dto.UserDto;
import com.vinz.energy.EnergyForge.dto.auth.CredentialsDto;
import com.vinz.energy.EnergyForge.dto.auth.SignUpDto;
import com.vinz.energy.EnergyForge.service.UserService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthProvider userAuthProvider;
	
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
