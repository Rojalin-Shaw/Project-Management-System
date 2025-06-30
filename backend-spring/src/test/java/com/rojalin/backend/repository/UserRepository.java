package com.rojalin.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rojalin.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);

}
