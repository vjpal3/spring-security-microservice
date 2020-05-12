package com.vrishalipal.microservices.securityjwtservice.web;

import javax.validation.Valid;
import static com.vrishalipal.microservices.securityjwtservice.security.SecurityConstants.TOKEN_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vrishalipal.microservices.securityjwtservice.model.AppUser;
import com.vrishalipal.microservices.securityjwtservice.payload.JWTLoginSuccessResponse;
import com.vrishalipal.microservices.securityjwtservice.payload.LoginRequest;
import com.vrishalipal.microservices.securityjwtservice.security.JwtTokenProvider;
import com.vrishalipal.microservices.securityjwtservice.services.AppUserService;
import com.vrishalipal.microservices.securityjwtservice.services.MapValidationErrorService;
import com.vrishalipal.microservices.securityjwtservice.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody AppUser user, BindingResult result) {
		
		//Validate passwords match
		userValidator.validate(user, result);
				
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) 
			return errorMap;
		
		AppUser newUser = appUserService.saveUser(user);
		return new ResponseEntity<AppUser>(newUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
		
		// Store the authentication object in ThreadLocal using SecurityContext
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	}
}
