package com.rojalin.backend.controller;

import java.security.PrivateKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rojalin.backend.model.User;
import com.rojalin.backend.repository.UserRepository;
import com.rojalin.backend.service.CustomUserDetailsImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomUserDetailsImpl customUserDetailsImpl;
	
	@PostMapping("/signup")
	public ResponseEntity<User>createUserHandler(@RequestBody User user) throws Exception{
		User isUserExist = userRepository.findByEmail(user.getEmail());
		if(isUserExist != null) {
			throw new Exception("Email already exist with another account");
		}
		
		User createdUser= new User();
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		createdUser.setEmail(user.getEmail());
		createdUser.setFullname(user.getFullname());
		User savedUser = userRepository.save(createdUser);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
}
