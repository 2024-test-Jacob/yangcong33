package com.yangcong.blog.controller;

import com.yangcong.blog.common.ApiResponse;
import com.yangcong.blog.dto.auth.CurrentUserResponse;
import com.yangcong.blog.dto.auth.LoginRequest;
import com.yangcong.blog.dto.auth.LoginResponse;
import com.yangcong.blog.security.AuthContext;
import com.yangcong.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @GetMapping("/me")
    public ApiResponse<CurrentUserResponse> currentUser() {
        return ApiResponse.success(authService.getCurrentUser(AuthContext.requireCurrentUserId()));
    }
}
