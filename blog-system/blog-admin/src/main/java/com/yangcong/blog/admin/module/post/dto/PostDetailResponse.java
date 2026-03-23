package com.yangcong.blog.admin.module.post.dto;

import java.time.LocalDateTime;

public record PostDetailResponse(
        Long id,
        String title,
        String summary,
        String content,
        String status,
        Long authorId,
        LocalDateTime publishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
