package com.yangcong.blog.service;

import com.yangcong.blog.common.BusinessException;
import com.yangcong.blog.dto.auth.CurrentUserResponse;
import com.yangcong.blog.dto.auth.LoginRequest;
import com.yangcong.blog.dto.auth.LoginResponse;
import com.yangcong.blog.entity.User;
import com.yangcong.blog.repository.UserRepository;
import com.yangcong.blog.security.TokenService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public AuthService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessException(401, "用户名或密码错误"));

        if (user.status() == null || user.status() != 1) {
            throw new BusinessException(403, "账号已被禁用");
        }

        if (!passwordEncoder.matches(request.password(), user.passwordHash())) {
            throw new BusinessException(401, "用户名或密码错误");
        }

        String token = tokenService.issueToken(user.id());
        return new LoginResponse(token, toCurrentUser(user));
    }

    public CurrentUserResponse getCurrentUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(404, "用户不存在"));
        return toCurrentUser(user);
    }

    private CurrentUserResponse toCurrentUser(User user) {
        return new CurrentUserResponse(user.id(), user.username(), user.nickname());
    }
}
