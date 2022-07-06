package com.testapp.conference.controller;

import com.testapp.conference.model.User;
import com.testapp.conference.model.UserRole;
import com.testapp.conference.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;


@RestController
@RequiredArgsConstructor
@Api(tags = "users")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "Login user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Invalid email/password supplied")
    })
    public ResponseEntity<String> login(
            @ApiParam("Email") @RequestHeader String email,
            @ApiParam("Password") @RequestHeader String password) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(email, password));
    }

    @PostMapping("/signup")
    @ApiOperation(value = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")
    })
    public ResponseEntity<String> signUp(@ApiParam("Signup User") @RequestBody @Valid User user) {
        user.setUserRoles(new ArrayList<>(Collections.singletonList(UserRole.USER_ROLE)));
        return ResponseEntity.status(HttpStatus.OK).body(userService.signUp(user));
    }

}
