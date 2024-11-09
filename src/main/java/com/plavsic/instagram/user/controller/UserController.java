package com.plavsic.instagram.user.controller;

import com.plavsic.instagram.user.dto.UserRequest;
import com.plavsic.instagram.user.dto.UserResponse;
import com.plavsic.instagram.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findByUsername(username),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

//    @PutMapping("/{username}")
//    public ResponseEntity<UserResponse> updateUserProfile(@Valid @RequestBody UserRequest userRequest,
//                                                          @PathVariable String username) {
//        UserResponse userResponse = userService.updateUser(username,userRequest);
//        return new ResponseEntity<>(userResponse,HttpStatus.OK);
//    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>("User deleted!",HttpStatus.OK);
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<UserResponse> followUser(@AuthenticationPrincipal UserDetails user, @PathVariable Long userId) {
        userService.followUser(user,userId);
        return null;
    }

    @PostMapping("/unfollow/{userId}")
    public ResponseEntity<UserResponse> unfollowUser(@AuthenticationPrincipal UserDetails user, @PathVariable Long userId) {
        userService.unfollowUser(user,userId);
        return null;
    }
}
