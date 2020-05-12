package com.vrishalipal.microservices.securityjwtservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vrishalipal.microservices.securityjwtservice.model.AppUser;
import com.vrishalipal.microservices.securityjwtservice.repositories.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private AppUserRepository appUserRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser user = appUserRepository.findByUsername(username);

		if(user == null)  new UsernameNotFoundException("User not found");
		
		//return object of type UserDetails 
		return user;  
	}
	
	@Transactional
	public AppUser loadUserById(Long id) {
		
		AppUser user = appUserRepository.getById(id);
		
		if(user == null)  new UsernameNotFoundException("User not found");
		
		return user; 
	}
}
