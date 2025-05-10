package com.bodima.project_lms.config;


import com.bodima.project_lms.exception.types.UserNotActiveException;
import com.bodima.project_lms.exception.types.UserNotVerifiedException;
import com.bodima.project_lms.model.UserEntity;
import com.bodima.project_lms.service.Impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Lazy
	@Autowired
	private AuthService authService;

	// authservice custom service
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, UserNotVerifiedException, UserNotActiveException {
		UserEntity user = authService.findUserByEmail(email); //user is entity class
		if (user != null && !user.getActive()) throw new UserNotActiveException();
		if (user != null && !user.getVerified()) throw new UserNotVerifiedException();
		if (user != null) {
			//a security feature (GrantedAuthority)
			List<GrantedAuthority> listAuthorities = new ArrayList<>();
			listAuthorities.add(new Role(user.getRole())); // adding role of user entity
			return new User(email, user.getPassword(), listAuthorities); // this user spring security user (security core)
		} else {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
	}

}