package com.yangcong.blog.module.auth.dto;

public record LoginResponse(String token, CurrentUserResponse userInfo) {
}
