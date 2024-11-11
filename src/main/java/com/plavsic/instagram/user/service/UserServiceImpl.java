package com.plavsic.instagram.user.service;

import com.plavsic.instagram.user.domain.Role;
import com.plavsic.instagram.user.domain.User;
import com.plavsic.instagram.user.dto.FollowResponse;
import com.plavsic.instagram.user.dto.UserRequest;
import com.plavsic.instagram.user.dto.UserResponse;
import com.plavsic.instagram.user.exception.EmailAlreadyExistsException;
import com.plavsic.instagram.user.exception.UserNotFoundException;
import com.plavsic.instagram.user.exception.UsernameAlreadyExistsException;
import com.plavsic.instagram.user.repository.RoleRepository;
import com.plavsic.instagram.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserResponse findByUsername(String username){
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getProfilePictureUrl(),
                        user.getDescription(),
                        user.getFollowers().stream().map(
                                follower -> new FollowResponse(
                                        follower.getId(),
                                        follower.getUsername())).collect(Collectors.toSet()),
                        user.getFollowing().stream().map(
                                follower -> new FollowResponse(
                                        follower.getId(),
                                        follower.getUsername())).collect(Collectors.toSet())
                ))
                .orElseThrow(UserNotFoundException::new);

        return userResponse;
    }


    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        checkIfUsernameAndEmailExists(userRequest.username(), userRequest.email());
        Role role = roleRepository.findByName("ROLE_USER").get();
        User user = modelMapper.map(userRequest, User.class);
        user.setId(null);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if(userRequest.profilePictureUrl().isEmpty() || userRequest.profilePictureUrl() == null){
            user.setProfilePictureUrl("https://static.vecteezy.com/system/resources/previews/002/002/403/non_2x/man-with-beard-avatar-character-isolated-icon-free-vector.jpg");
        }else{
            user.setProfilePictureUrl(userRequest.profilePictureUrl());
        }
        user.setDescription(userRequest.description());
        user.setRoles(new HashSet<>(List.of(role)));
        user = userRepository.save(user);
        return null;
    }

    @Override
    public UserResponse updateUser(String username, UserRequest userRequest) {
        return null; // TODO
    }



    @Override
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        for(User follower: user.getFollowers()){
            follower.getFollowing().remove(user);
        }

        for(User following: user.getFollowing()){
            following.getFollowers().remove(user);
        }
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void followUser(UserDetails currentUser, Long userToFollow) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(UserNotFoundException::new);
        User userFollow = userRepository.findById(userToFollow).orElseThrow(UserNotFoundException::new);
        if(Objects.equals(user.getUsername(), userFollow.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        user.follow(userFollow);
        userRepository.save(user);
    }

    @Override
    public void unfollowUser(UserDetails currentUser, Long userToUnfollow) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(UserNotFoundException::new);
        User userUnfollow = userRepository.findById(userToUnfollow).orElseThrow(UserNotFoundException::new);
        if(Objects.equals(user.getUsername(), userUnfollow.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        user.unfollow(userUnfollow);
        userRepository.save(user);
    }


    private void checkIfUsernameAndEmailExists(String username, String email) {
        if(userRepository.existsByUsername(username)){
            throw new UsernameAlreadyExistsException(username);
        }

        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException(email);
        }
    }


}
