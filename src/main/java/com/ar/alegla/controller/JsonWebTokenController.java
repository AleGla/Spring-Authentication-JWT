package com.ar.alegla.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ar.alegla.model.RequestToken;
import com.ar.alegla.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class JsonWebTokenController {
	
	private static final Logger log = LoggerFactory.getLogger(JsonWebTokenController.class);

	@PostMapping("/token")
	public User token(@RequestBody RequestToken user) {
		
		String token = getJWTToken(user.getUser());
		User newUser = new User();
		newUser.setUser(user.getUser());
		newUser.setToken(token);	
		log.info("Response -> " + newUser.toString());
		return newUser;
	}
	
	
	private String getJWTToken(String username) {
		String secretKey = "alegla/github/Secretkey.key";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("aleglaJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "JWT " + token;
	}
}


