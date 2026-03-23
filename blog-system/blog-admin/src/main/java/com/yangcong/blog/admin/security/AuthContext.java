package com.yangcong.blog.admin.security;

import com.yangcong.blog.admin.common.BusinessException;

public final class AuthContext {

    private static final ThreadLocal<Long> CURRENT_USER = new ThreadLocal<>();

    private AuthContext() {
    }

    public static void setCurrentUserId(Long userId) {
        CURRENT_USER.set(userId);
    }

    public static Long requireCurrentUserId() {
        Long userId = CURRENT_USER.get();
        if (userId == null) {
            throw new BusinessException(401, "未登录或登录状态已失效");
        }
        return userId;
    }

    public static void clear() {
        CURRENT_USER.remove();
    }
}
