package com.yangcong.blog.security;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final ConcurrentMap<String, Long> tokenStore = new ConcurrentHashMap<>();

    public String issueToken(Long userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        tokenStore.put(token, userId);
        return token;
    }

    public Optional<Long> resolveUserId(String token) {
        return Optional.ofNullable(tokenStore.get(token));
    }
}
