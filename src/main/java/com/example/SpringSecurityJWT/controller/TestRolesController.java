package com.example.SpringSecurityJWT.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRolesController {
	
	@GetMapping("/accessAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public String accesAdmin() {
		return "You have logged in as an ADMIN";
	}
	
	@GetMapping("/accessUser")
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	public String accesUser() {
		return "You have logged in as an USER";
	}
	
	@GetMapping("/accessGuest")
	@PreAuthorize("hasAnyRole('GUEST', 'USER', 'ADMIN')")
	public String accessGuest() {
		return "You have logged in as an GUEST";
	}

}
