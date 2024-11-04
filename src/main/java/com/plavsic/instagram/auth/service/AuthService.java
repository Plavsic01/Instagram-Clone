package com.plavsic.instagram.auth.service;

import com.plavsic.instagram.auth.dto.LoginRequest;
import com.plavsic.instagram.auth.dto.RegisterRequest;
import com.plavsic.instagram.auth.userDetails.CustomUserDetails;
import com.plavsic.instagram.auth.util.JwtTokenUtil;
import com.plavsic.instagram.user.dto.UserRequest;
import com.plavsic.instagram.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${secret}")
    private String secret;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),loginRequest.password())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(secret);
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.
                loadUserByUsername(loginRequest.username());
        return jwtTokenUtil.generateToken(customUserDetails);
    }

    public String signup(RegisterRequest registerRequest) {
        userService.save(new UserRequest(
                registerRequest.username(),
                registerRequest.password(),
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.email(),
                null)); // ovo ce se promeniti
        return "User created";
    }

}
