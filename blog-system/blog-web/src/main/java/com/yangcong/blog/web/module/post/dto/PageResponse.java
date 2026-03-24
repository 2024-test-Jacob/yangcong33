package com.yangcong.blog.web.module.post.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> records,
        long total,
        int pageNum,
        int pageSize,
        long totalPages
) {
}
