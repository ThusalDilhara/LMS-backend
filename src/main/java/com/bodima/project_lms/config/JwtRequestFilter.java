package com.bodima.project_lms.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

// filter things after receive request  (not sign in all secure api)
@Component
public class JwtRequestFilter extends OncePerRequestFilter { // OncePerRequestFilter - run every request

    @Lazy
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    //additionaly added constructer
    public JwtRequestFilter(@Lazy JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenRefreshProcessEndDaysAfterTokenIssued}")
    private int tokenRefreshProcessEndDaysAfterTokenIssued;


    private void allowForRefreshToken(ExpiredJwtException ex, jakarta.servlet.http.HttpServletRequest request) {

        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());

    }

    //set the defined date length to token issued date, to get the refresh process cancelling date
    private Date getTokenRefreshProcessExpireDate(String token) {

        String decodeToken = new String(Base64.getDecoder().decode(token.split("\\.")[1]), StandardCharsets.UTF_8);
        JsonObject json = new Gson().fromJson(decodeToken, JsonObject.class);
        int noOfDays = tokenRefreshProcessEndDaysAfterTokenIssued; //i.e two weeks
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(json.get("iat").getAsLong() * 1000));
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        return calendar.getTime();
    }

    // after request check header
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        //debugging
        System.out.println("Auth header received: " + request.getHeader("Authorization"));
        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) { //not be null and start with bearer in token
            jwtToken = requestTokenHeader.substring(7); //get from 7th position
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {

                String isRefreshToken = request.getHeader("isRefreshToken");
                String requestURL = request.getRequestURL().toString();
                Date tokenRefreshProcessExpireDate = getTokenRefreshProcessExpireDate(jwtToken);

                // allow for Refresh Token creation if following conditions are true.
                if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshToken")) {
                    try {
                        if (new Date().compareTo(tokenRefreshProcessExpireDate) >= 0) {
                            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "JWT Token has expired");
                        } else {
                            allowForRefreshToken(e, request);
                        }
                    } catch (ExpiredJwtException ex) {
                        System.out.println("JWT Token has expired");
                    }
                } else {
                    request.setAttribute("exception", e);
                    System.out.println("JWT Token has expired");
                }
            } catch (Exception e) {
                logger.error("JWT Token processing error", e);
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        //Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                //inbuild = pass user details to this
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response); //do other things in chain
    }
}