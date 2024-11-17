package com.plavsic.instagram.post.dto.post;

import java.time.LocalDateTime;

public record PostResponse(Long id,
                           String description,
                           LocalDateTime createdAt,
                           String imageUrl,
                           Integer numberOfLikes,
                           Boolean isLikedByCurrentUser) {
}
