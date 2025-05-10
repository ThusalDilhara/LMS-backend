package com.bodima.project_lms.controller;


import com.bodima.project_lms.config.JwtAuthenticationController;
import com.bodima.project_lms.config.JwtRequest;
import com.bodima.project_lms.config.JwtResponse;
import com.bodima.project_lms.dto.ResponseDto;
import com.bodima.project_lms.model.UserEntity;
import com.bodima.project_lms.service.Impl.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.bodima.project_lms.dto.AuthDto;
import com.bodima.project_lms.dto.UserDto;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/api/v1")
public class AuthAPI {

    @Autowired
    private JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    private AuthService authService;

    //if username and pawwwords correct return token
    @PostMapping("/signIn")
    public ResponseEntity<ResponseDto> signIn(@RequestBody @Valid AuthDto authDto) throws DisabledException,BadCredentialsException{

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(authDto.getEmail());
        jwtRequest.setPassword(authDto.getPassword());

        // generete token
        JwtResponse jwtResponse = jwtAuthenticationController.generateAuthenticationToken(jwtRequest);

        //get user with some details (any)
        UserEntity user = authService.findUserByEmail(authDto.getEmail());
        Map<String,Object> map = new HashMap<>();
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("role", user.getRole());
        map.put("email", user.getEmail());
        map.put("token", jwtResponse.getToken());

        return new ResponseEntity<>(new ResponseDto("success","200", map),HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody UserDto userDto) throws Exception{
        //save user in db // if want add more thngs eg-: encrypt passwords ... etc
        return new ResponseEntity<>(authService.signUp(userDto), HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseDto> refreshToken(HttpServletRequest request) throws Exception{

        String refreshedToken = jwtAuthenticationController.getRefreshToken(request);
        return new ResponseEntity<>(new ResponseDto("success","200",refreshedToken), HttpStatus.OK);
    }


    //check this
    @GetMapping("/user-details")
    public ResponseEntity<ResponseDto> getUserDetails(HttpServletRequest request) {
        // Get username from authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Get user details
        UserEntity user = authService.findUserByEmail(email);

        // Create response with user details
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", user.getId());
        userDetails.put("firstName", user.getFirstName());
        userDetails.put("lastName", user.getLastName());
        userDetails.put("email", user.getEmail());
        userDetails.put("role", user.getRole());
        userDetails.put("active", user.getActive());
        userDetails.put("verified", user.getVerified());
        userDetails.put("createDateTime", user.getCreateDateTime());

        return new ResponseEntity<>(new ResponseDto("success", "200", userDetails), HttpStatus.OK);
    }
}
