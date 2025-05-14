package com.bodima.project_lms.config;



import com.bodima.project_lms.model.UserEntity;
import com.bodima.project_lms.service.Impl.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// just only class
// token related methods
@Component

public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	@Value("${jwt.secret}")
	private String secret;  //secrete

	@Value("${jwt.tokenExpireDate}")
	private int tokenValidity; //time valid

	@Lazy
	@Autowired
	private AuthService authService;

	//
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// check added claims optional
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	//
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		if (userDetails.getAuthorities() != null && !userDetails.getAuthorities().isEmpty()) {
			String roles = userDetails.getAuthorities().stream()
					.map(authority -> authority.getAuthority())
					.collect(Collectors.joining(","));

			System.out.println("Adding roles to token: " + roles);
			claims.put("role", roles);
		}

		// Get user entity and add firstName to claims
		UserEntity user = authService.findUserByEmail(userDetails.getUsername());		if (user != null) {
			claims.put("firstName", user.getFirstName());
			claims.put("lastName",user.getLastName());
			claims.put("id",user.getId());
		}



		return doGenerateToken(claims, userDetails.getUsername());
	}


	private String doGenerateToken(Map<String, Object> claims, String subject) {

		// verify is this token create by this backend
		//miliseconds * 1000 = seconds
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenValidity*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenValidity*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}