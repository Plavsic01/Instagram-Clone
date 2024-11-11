package com.plavsic.instagram.auth.dto;

import com.plavsic.instagram.user.dto.UserResponse;

public record LoginResponse(String token, UserResponse user) {}
