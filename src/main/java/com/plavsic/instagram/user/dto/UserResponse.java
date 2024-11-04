package com.plavsic.instagram.user.dto;


import java.util.Set;

public record UserResponse(Long id,
                           String username,
                           String firstName,
                           String lastName,
                           String email,
                           String profilePictureUrl,
                           Set<FollowResponse> followers,
                           Set<FollowResponse> following){}