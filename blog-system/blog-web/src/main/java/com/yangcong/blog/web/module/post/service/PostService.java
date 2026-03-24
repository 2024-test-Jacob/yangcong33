package com.yangcong.blog.web.module.post.service;

import com.yangcong.blog.web.module.post.dto.PageResponse;
import com.yangcong.blog.web.module.post.dto.PostDetailResponse;
import com.yangcong.blog.web.module.post.dto.PostListResponse;

public interface PostService {

    PageResponse<PostListResponse> listPublishedPosts(int pageNum, int pageSize);

    PostDetailResponse getPublishedPost(Long id);
}
