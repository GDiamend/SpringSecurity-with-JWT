package com.example.SpringSecurityJWT.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.SpringSecurityJWT.security.jwt.JwtUtils;
import com.example.SpringSecurityJWT.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		String tokenHeader = request.getHeader("Authorization");
		
		if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			String token = tokenHeader.substring(7);
			
			if(this.jwtUtils.isTokeValid(token)) {
				String username = this.jwtUtils.getUserNameFromToken(token);
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
