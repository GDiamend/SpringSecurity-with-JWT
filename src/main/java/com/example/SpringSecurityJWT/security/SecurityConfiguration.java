package com.example.SpringSecurityJWT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.SpringSecurityJWT.security.filters.JwtAuthenticationFilter;
import com.example.SpringSecurityJWT.security.filters.JwtAuthorizationFilter;
import com.example.SpringSecurityJWT.security.jwt.JwtUtils;
import com.example.SpringSecurityJWT.service.UserDetailsServiceImpl;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
		
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(this.jwtUtils);
		jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		
		return httpSecurity
				.csrf(config -> config.disable())
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/hello").permitAll();
					auth.anyRequest().authenticated();
				})
				.sessionManagement(session ->{
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.addFilter(jwtAuthenticationFilter)
				.addFilterBefore(this.jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
		
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(this.userDetailsService)
				.passwordEncoder(passwordEncoder)
				.and()
				.build();
	}
	
	

}
