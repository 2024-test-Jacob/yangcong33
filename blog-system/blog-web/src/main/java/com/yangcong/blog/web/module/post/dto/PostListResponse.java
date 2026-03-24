package com.yangcong.blog.web.module.post.dto;

import java.time.LocalDateTime;

public record PostListResponse(
        Long id,
        String title,
        String summary,
        Long authorId,
        LocalDateTime publishedAt
) {
}
