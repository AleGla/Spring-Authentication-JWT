package com.ar.alegla.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ar.alegla.security.JWTAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)				
				.authorizeRequests()				
				.antMatchers(HttpMethod.POST, "/token").permitAll()				
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
		            Map<String, Object> responseMap = new HashMap<>();
		            ObjectMapper mapper = new ObjectMapper();
		            responseMap.put("error", response.getStatus());
		            responseMap.put("message", "Unauthorized");
		            response.setHeader("content-type", "application/json");
		            String responseMsg = mapper.writeValueAsString(responseMap);
		            response.getWriter().write(responseMsg);
		        });
		    }	
}
