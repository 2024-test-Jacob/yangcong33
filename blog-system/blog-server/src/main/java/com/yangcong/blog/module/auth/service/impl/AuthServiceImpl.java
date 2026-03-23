package com.yangcong.blog.module.auth.service.impl;

import com.yangcong.blog.common.BusinessException;
import com.yangcong.blog.module.auth.dto.CurrentUserResponse;
import com.yangcong.blog.module.auth.dto.LoginRequest;
import com.yangcong.blog.module.auth.dto.LoginResponse;
import com.yangcong.blog.module.auth.model.User;
import com.yangcong.blog.module.auth.repository.UserRepository;
import com.yangcong.blog.module.auth.security.TokenService;
import com.yangcong.blog.module.auth.service.AuthService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public AuthServiceImpl(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessException(401, "用户名或密码错误"));

        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException(403, "账号已被禁用");
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BusinessException(401, "用户名或密码错误");
        }

        String token = tokenService.issueToken(user.getId());
        return new LoginResponse(token, toCurrentUser(user));
    }

    @Override
    public CurrentUserResponse getCurrentUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(404, "用户不存在"));
        return toCurrentUser(user);
    }

    private CurrentUserResponse toCurrentUser(User user) {
        return new CurrentUserResponse(user.getId(), user.getUsername(), user.getNickname());
    }
}
