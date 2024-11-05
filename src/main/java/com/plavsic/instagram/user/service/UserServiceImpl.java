package com.plavsic.instagram.user.service;

import com.plavsic.instagram.user.domain.Role;
import com.plavsic.instagram.user.domain.User;
import com.plavsic.instagram.user.dto.FollowResponse;
import com.plavsic.instagram.user.dto.UserRequest;
import com.plavsic.instagram.user.dto.UserResponse;
import com.plavsic.instagram.user.exception.UserNotFoundException;
import com.plavsic.instagram.user.repository.RoleRepository;
import com.plavsic.instagram.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Optional<UserResponse> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserResponse> findAll() {
        return List.of();
    }


    @Override
    @Transactional
    public UserResponse save(UserRequest userRequest) {
        Role role = roleRepository.findByName("ROLE_USER").get();
        User user = modelMapper.map(userRequest, User.class);
        user.setId(null);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setProfilePictureUrl("url://path");
        user.setRoles(new HashSet<>(List.of(role)));
        user = userRepository.save(user);
        return null;
    }

    @Override
    @Transactional
    public UserResponse followUser(UserDetails currentUser,Long userToFollow) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(UserNotFoundException::new);
        User userFollow = userRepository.findById(userToFollow).orElseThrow(UserNotFoundException::new);
        if(Objects.equals(user.getUsername(), userFollow.getUsername())) {
            return null;
        }
        user.follow(userFollow);
        userRepository.save(user);
        return null;
    }

    @Override
    public UserResponse unfollowUser(UserDetails currentUser,Long userToUnfollow) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(UserNotFoundException::new);
        User userUnfollow = userRepository.findById(userToUnfollow).orElseThrow(UserNotFoundException::new);
        user.unfollow(userUnfollow);
        userRepository.save(user);
        return null;
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        return null;
    }




    @Override
    public boolean delete(Long id) {
        return false;
    }





    public UserResponse findByUsername(String username){
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getProfilePictureUrl(),
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
}
