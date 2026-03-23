package com.yangcong.blog.module.auth.model;

import java.time.LocalDateTime;

public record User(
        Long id,
        String username,
        String passwordHash,
        String nickname,
        Integer status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
