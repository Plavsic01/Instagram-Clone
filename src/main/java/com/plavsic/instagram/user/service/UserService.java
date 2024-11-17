package com.plavsic.instagram.user.service;


import com.plavsic.instagram.user.dto.FollowResponse;
import com.plavsic.instagram.user.dto.SearchResponse;
import com.plavsic.instagram.user.dto.UserRequest;
import com.plavsic.instagram.user.dto.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@org.springframework.stereotype.Service
public interface UserService {
    UserResponse findByUsername(String username);
    List<SearchResponse> findByUsernameContaining(String username);
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(String username, UserRequest userRequest);
    void deleteUser(String username);
    FollowResponse followUser(UserDetails currentUser, Long id);
    FollowResponse unfollowUser(UserDetails currentUser, Long id);
}







