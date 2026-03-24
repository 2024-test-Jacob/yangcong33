package com.yangcong.blog.web.module.post.controller;

import com.yangcong.blog.web.common.ApiResponse;
import com.yangcong.blog.web.module.post.dto.PageResponse;
import com.yangcong.blog.web.module.post.dto.PostDetailResponse;
import com.yangcong.blog.web.module.post.dto.PostListResponse;
import com.yangcong.blog.web.module.post.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<PageResponse<PostListResponse>> listPosts(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ApiResponse.success(postService.listPublishedPosts(pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDetailResponse> getPost(@PathVariable Long id) {
        return ApiResponse.success(postService.getPublishedPost(id));
    }
}
