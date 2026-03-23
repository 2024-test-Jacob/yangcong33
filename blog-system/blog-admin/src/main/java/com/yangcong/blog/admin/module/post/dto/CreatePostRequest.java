package com.yangcong.blog.admin.module.post.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(
        @NotBlank(message = "文章标题不能为空")
        String title,
        String summary,
        @NotBlank(message = "文章内容不能为空")
        String content,
        String status
) {
}
