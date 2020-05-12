package com.vrishalipal.microservices.securityjwtservice.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vrishalipal.microservices.securityjwtservice.model.AppUser;
import com.vrishalipal.microservices.securityjwtservice.services.AppUserService;
import com.vrishalipal.microservices.securityjwtservice.services.MapValidationErrorService;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private AppUserService appUserService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody AppUser user, BindingResult result) {
				
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) 
			return errorMap;
		
		AppUser newUser = appUserService.saveUser(user);
		return new ResponseEntity<AppUser>(newUser, HttpStatus.CREATED);
	}
}
