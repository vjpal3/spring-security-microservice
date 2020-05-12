package com.vrishalipal.microservices.securityjwtservice.services;

import java.sql.SQLException;
import java.util.Set;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vrishalipal.microservices.securityjwtservice.exceptions.UsernameAlreadyExistsException;
import com.vrishalipal.microservices.securityjwtservice.model.AppUser;
import com.vrishalipal.microservices.securityjwtservice.repositories.AppUserRepository;

@Service
public class AppUserService {
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public AppUser saveUser(AppUser newUser) {
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setConfirmPassword("");
						
			return appUserRepository.save(newUser);
		} 
			catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists!");
		}	
	}
	

}
