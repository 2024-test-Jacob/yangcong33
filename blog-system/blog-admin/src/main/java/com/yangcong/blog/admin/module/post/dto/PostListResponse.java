package com.yangcong.blog.admin.module.post.dto;

import java.time.LocalDateTime;

public record PostListResponse(
        Long id,
        String title,
        String summary,
        String status,
        Long authorId,
        LocalDateTime publishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
