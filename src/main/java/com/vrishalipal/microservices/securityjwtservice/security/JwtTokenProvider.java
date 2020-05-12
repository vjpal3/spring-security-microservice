package com.vrishalipal.microservices.securityjwtservice.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.vrishalipal.microservices.securityjwtservice.model.AppUser;
import static com.vrishalipal.microservices.securityjwtservice.security.SecurityConstants.EXPIRATION_TIME;
import static com.vrishalipal.microservices.securityjwtservice.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {
	
	public String generateToken(Authentication authentication) {
		
		AppUser user = (AppUser)authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
		
		String userId = Long.toString(user.getId());
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", (Long.toString(user.getId())));
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());
		
		// build a token for a valid user
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}
}
