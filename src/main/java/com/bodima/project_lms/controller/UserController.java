package com.bodima.project_lms.controller;

import com.bodima.project_lms.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @PostMapping("/register")
public ResponseEntity<String> registerUSer(@RequestBody User user) {

        return null;
    }

}
