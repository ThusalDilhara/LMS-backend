package com.bodima.project_lms.config;


import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtAuthenticationController {

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;     //custom class

    @Autowired
    private UserDetailsService jwtUserDetailsService;


    //make token
    //JwtRequest = dto only
    //authenticationRequest = method
    public JwtResponse generateAuthenticationToken(JwtRequest authenticationRequest)
            throws DisabledException, BadCredentialsException {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        //to generete token need user details
        //this userDetails have loged user's details
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }

    // check username or password is null or not

    public void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            // in user custom object we define attribute active or not is user is not active through this exception
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            // if username or password incorrect
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    } // now this method know is user okay or not

    public String getRefreshToken(HttpServletRequest request) throws Exception {
        String requestTokenHeader = request.getHeader("Authorization");
        String token;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);
            DefaultClaims claims;
            if (request.getAttribute("claims") != null) {
                claims = (DefaultClaims) request.getAttribute("claims");
            } else {
                claims = (DefaultClaims) jwtTokenUtil.getAllClaimsFromToken(token);
            }
            return generateRefreshToken(token, claims);
        } else {
            throw new Exception("token cannot be empty!");
        }
    }

    public String generateRefreshToken(String token, DefaultClaims claims) {
        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        return jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
}