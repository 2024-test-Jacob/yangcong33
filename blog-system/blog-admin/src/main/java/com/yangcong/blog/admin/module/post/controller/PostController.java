package com.yangcong.blog.admin.module.post.controller;

import com.yangcong.blog.admin.common.ApiResponse;
import com.yangcong.blog.admin.module.post.dto.CreatePostRequest;
import com.yangcong.blog.admin.module.post.dto.PageResponse;
import com.yangcong.blog.admin.module.post.dto.PostDetailResponse;
import com.yangcong.blog.admin.module.post.dto.PostListResponse;
import com.yangcong.blog.admin.module.post.dto.UpdatePostRequest;
import com.yangcong.blog.admin.module.post.service.PostService;
import com.yangcong.blog.admin.security.AuthContext;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<PageResponse<PostListResponse>> listPosts(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String status) {
        return ApiResponse.success(postService.listPosts(pageNum, pageSize, status));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDetailResponse> getPost(@PathVariable Long id) {
        return ApiResponse.success(postService.getPost(id));
    }

    @PostMapping
    public ApiResponse<PostDetailResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        Long authorId = AuthContext.requireCurrentUserId();
        return ApiResponse.success(postService.createPost(request, authorId));
    }

    @PutMapping("/{id}")
    public ApiResponse<PostDetailResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request) {
        return ApiResponse.success(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ApiResponse.success(null);
    }
}
