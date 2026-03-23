package com.yangcong.blog.module.auth.service;

import com.yangcong.blog.module.auth.dto.CurrentUserResponse;
import com.yangcong.blog.module.auth.dto.LoginRequest;
import com.yangcong.blog.module.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    CurrentUserResponse getCurrentUser(Long userId);
}
