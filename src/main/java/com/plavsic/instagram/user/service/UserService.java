package com.plavsic.instagram.user.service;


import com.plavsic.instagram.user.dto.UserRequest;
import com.plavsic.instagram.user.dto.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

@org.springframework.stereotype.Service
public interface UserService extends Service<UserRequest,UserResponse> {

    UserResponse save(UserRequest userRequest);
    UserResponse followUser(UserDetails currentUser, Long id);
    UserResponse unfollowUser(UserDetails currentUser,Long id);
}
