package com.vrishalipal.microservices.securityjwtservice.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vrishalipal.microservices.securityjwtservice.model.AppUser;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AppUser.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		AppUser user = (AppUser) object;
		
		if(user.getPassword().length() < 6) {
			errors.rejectValue("password", "Length", "Password must be at least 6 characters");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Match", "Passwords must match");
		}
		
	}
	

}
