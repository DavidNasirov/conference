package com.testapp.conference.controller;

import com.testapp.conference.model.User;
import com.testapp.conference.model.UserRole;
import com.testapp.conference.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader String email, @RequestHeader String password) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(email, password));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid User user) {
        user.setUserRoles(new ArrayList<>(Collections.singletonList(UserRole.USER_ROLE)));
        return ResponseEntity.status(HttpStatus.OK).body(userService.signUp(user));
    }

}
