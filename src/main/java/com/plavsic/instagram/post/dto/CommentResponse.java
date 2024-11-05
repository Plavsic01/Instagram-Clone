package com.plavsic.instagram.post.dto;

import java.time.LocalDateTime;

public record CommentResponse(Long id, String username, String content, LocalDateTime createdAt) {
}
