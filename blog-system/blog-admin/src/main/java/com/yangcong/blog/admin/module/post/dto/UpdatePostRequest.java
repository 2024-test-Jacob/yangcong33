package com.yangcong.blog.admin.module.post.dto;

public record UpdatePostRequest(
        String title,
        String summary,
        String content,
        String status
) {
}
