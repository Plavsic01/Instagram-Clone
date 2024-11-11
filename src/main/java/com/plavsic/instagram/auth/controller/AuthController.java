package com.plavsic.instagram.auth.controller;


import com.plavsic.instagram.auth.dto.LoginRequest;
import com.plavsic.instagram.auth.dto.LoginResponse;
import com.plavsic.instagram.auth.service.AuthService;
import com.plavsic.instagram.user.dto.UserRequest;
import com.plavsic.instagram.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) {
        return new ResponseEntity<>(new LoginResponse(authService.login(login),userService.findByUsername(login.username())), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserRequest register, BindingResult result){
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }
        return new ResponseEntity<>(authService.signup(register),HttpStatus.CREATED);
    }


}
