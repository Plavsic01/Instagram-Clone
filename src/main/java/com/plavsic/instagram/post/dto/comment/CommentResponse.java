package com.plavsic.instagram.post.dto.comment;

import java.time.LocalDateTime;

public record CommentResponse(Long id, String username,String profilePictureUrl, String content, LocalDateTime createdAt) {
}
