package com.yangcong.blog.admin.module.post.service;

import com.yangcong.blog.admin.module.post.dto.CreatePostRequest;
import com.yangcong.blog.admin.module.post.dto.PageResponse;
import com.yangcong.blog.admin.module.post.dto.PostDetailResponse;
import com.yangcong.blog.admin.module.post.dto.PostListResponse;
import com.yangcong.blog.admin.module.post.dto.UpdatePostRequest;

public interface PostService {

    PageResponse<PostListResponse> listPosts(int pageNum, int pageSize, String status);

    PostDetailResponse getPost(Long id);

    PostDetailResponse createPost(CreatePostRequest request, Long authorId);

    PostDetailResponse updatePost(Long id, UpdatePostRequest request);

    void deletePost(Long id);
}
