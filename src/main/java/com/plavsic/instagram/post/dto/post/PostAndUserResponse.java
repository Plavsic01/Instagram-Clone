package com.plavsic.instagram.post.dto.post;

import java.time.LocalDateTime;

public record PostAndUserResponse(Long id,
                                  String description,
                                  LocalDateTime createdAt,
                                  String imageUrl,
                                  Integer numberOfLikes,
                                  Boolean isLikedByCurrentUser,
                                  String username,
                                  String profilePictureUrl
                                  ) {
}
