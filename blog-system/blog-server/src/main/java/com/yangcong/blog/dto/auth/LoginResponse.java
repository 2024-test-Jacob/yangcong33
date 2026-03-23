package com.yangcong.blog.dto.auth;

public record LoginResponse(String token, CurrentUserResponse userInfo) {
}
