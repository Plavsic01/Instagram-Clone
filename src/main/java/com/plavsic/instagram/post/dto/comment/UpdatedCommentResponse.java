package com.plavsic.instagram.post.dto.comment;

import java.time.LocalDateTime;

public record UpdatedCommentResponse(String content, LocalDateTime createdAt) {
}
