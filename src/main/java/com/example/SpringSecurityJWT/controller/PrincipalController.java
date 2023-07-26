package com.example.SpringSecurityJWT.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringSecurityJWT.models.ERole;
import com.example.SpringSecurityJWT.models.RoleEntity;
import com.example.SpringSecurityJWT.models.UserEntity;
import com.example.SpringSecurityJWT.repositories.UserRepository;
import com.example.SpringSecurityJWT.request.CreateUserDTO;

import jakarta.validation.Valid;

@RestController
public class PrincipalController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello world not secured";
	}
	
	@GetMapping("/helloSecured")
	public String helloSecured() {
		return "Hello world nsecured";
	}
	
	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
		
		Set<RoleEntity> roles = createUserDTO.getRoles().stream()
				.map(role -> RoleEntity.builder()
						.name(ERole.valueOf(role))
						.build())
				.collect(Collectors.toSet());
		
		UserEntity userEntity = UserEntity.builder()
				.userName(createUserDTO.getUserName())
				.password(createUserDTO.getPassword())
				.email(createUserDTO.getEmail())
				.roles(roles)
				.build();
		
		this.userRepository.save(userEntity);
		
		return ResponseEntity.ok(userEntity);
	}
	
	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestParam String id) {
		userRepository.deleteById(Long.parseLong(id));
		return "User deleted satisfactory";
	}

}
