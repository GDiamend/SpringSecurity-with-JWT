package com.example.SpringSecurityJWT;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.SpringSecurityJWT.models.ERole;
import com.example.SpringSecurityJWT.models.RoleEntity;
import com.example.SpringSecurityJWT.models.UserEntity;
import com.example.SpringSecurityJWT.repositories.UserRepository;

@SpringBootApplication
public class SpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args); }
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Bean
	CommandLineRunner init(){
		return args -> {
			UserEntity userEntity = UserEntity.builder()
					.email("asds@sads.com")
					.username("pepe")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();	
			
			UserEntity userEntity2 = UserEntity.builder()
					.email("asds@sads.com")
					.username("rocío")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.USER.name()))
							.build()))
					.build();
			
			UserEntity userEntity3 = UserEntity.builder()
					.email("asds@sads.com")
					.username("raúl")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.GUEST.name()))
							.build()))
					.build();
			
			userRepository.save(userEntity);
			userRepository.save(userEntity2);
			userRepository.save(userEntity3);

		};
	}
}
