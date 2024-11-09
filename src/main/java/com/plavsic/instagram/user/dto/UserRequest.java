package com.plavsic.instagram.user.dto;

import jakarta.validation.constraints.*;

public record UserRequest(@NotNull @NotEmpty @NotBlank String username,
                          @NotNull @Size(min = 8, max = 20) String password,
                          @NotNull @NotEmpty @NotBlank String firstName,
                          @NotNull @NotEmpty @NotBlank String lastName,
                          @NotNull @Email @NotEmpty @NotBlank String email,
                          String profilePictureUrl,
                          String description){}