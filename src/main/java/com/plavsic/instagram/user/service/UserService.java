package com.plavsic.instagram.user.service;


import com.plavsic.instagram.user.dto.UserRequest;
import com.plavsic.instagram.user.dto.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;


@org.springframework.stereotype.Service
public interface UserService {
    UserResponse findByUsername(String username);
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(String username, UserRequest userRequest);
    void deleteUser(String username);
    void followUser(UserDetails currentUser, Long id);
    void unfollowUser(UserDetails currentUser, Long id);
}







