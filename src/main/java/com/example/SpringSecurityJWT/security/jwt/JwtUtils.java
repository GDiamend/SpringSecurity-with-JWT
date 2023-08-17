package com.example.SpringSecurityJWT.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	
	@Value("${jwt.time.expiration}")
	private String timeExpiration;
	
	//Access token generator
	public String generateAccessToken(String userName) {
		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(this.timeExpiration)))
				.signWith(this.getSignatureKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	//Token's sign maker
	public Key getSignatureKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	//Token's Validation
	public boolean isTokeValid(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(this.getSignatureKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
			return true;
		} catch(Exception e) {
			log.error("Token invalido, error".concat(e.getMessage()));
			 return false;
		}
	}
	
	//Get token's userName
	public String getUserNameFromToken(String token) {
		return this.getClaim(token, Claims::getSubject);
	}
	
	//Get specific claim
	public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
		Claims claims = this.extractAllClaims(token);
		return claimsTFunction.apply(claims);
		
	}
	
	//Get token's claims
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(this.getSignatureKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
}
