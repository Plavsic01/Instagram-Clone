package com.plavsic.instagram.user.dto;

import jakarta.validation.constraints.*;

public record UserRequest(@NotBlank String username,
                          @NotBlank @Size(min = 8, max = 20,message = "Password must be between 8 and 20 characters!") String password,
                          @NotBlank(message = "First name must not be empty") String firstName,
                          @NotBlank(message = "Last name must not be empty") String lastName,
                          @Email @NotBlank String email,
                          String profilePictureUrl,
                          String description){}