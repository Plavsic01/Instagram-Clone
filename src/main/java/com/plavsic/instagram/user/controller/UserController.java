package com.plavsic.instagram.user.controller;

import com.plavsic.instagram.user.dto.FollowResponse;
import com.plavsic.instagram.user.dto.SearchResponse;
import com.plavsic.instagram.user.dto.UserResponse;
import com.plavsic.instagram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findByUsername(username),HttpStatus.OK);
    }

//    @PutMapping("/{username}")
//    public ResponseEntity<UserResponse> updateUserProfile(@Valid @RequestBody UserRequest userRequest,
//                                                          @PathVariable String username) {
//        UserResponse userResponse = userService.updateUser(username,userRequest);
//        return new ResponseEntity<>(userResponse,HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<List<SearchResponse>> searchByUsername(@RequestParam(name = "username") String username) {
        return new ResponseEntity<>(userService.findByUsernameContaining(username),HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>("User deleted!",HttpStatus.OK);
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<FollowResponse> followUser(@AuthenticationPrincipal UserDetails user, @PathVariable Long userId) {
        FollowResponse followResponse = userService.followUser(user,userId);
        return new ResponseEntity<>(followResponse,HttpStatus.OK);
    }

    @PostMapping("/unfollow/{userId}")
    public ResponseEntity<FollowResponse> unfollowUser(@AuthenticationPrincipal UserDetails user, @PathVariable Long userId) {
        FollowResponse followResponse = userService.unfollowUser(user,userId);
        return new ResponseEntity<>(followResponse,HttpStatus.OK);
    }
}
