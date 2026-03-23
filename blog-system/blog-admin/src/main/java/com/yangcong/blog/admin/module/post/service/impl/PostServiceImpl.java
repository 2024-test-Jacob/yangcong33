package com.yangcong.blog.admin.module.post.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangcong.blog.admin.common.BusinessException;
import com.yangcong.blog.admin.module.post.dto.CreatePostRequest;
import com.yangcong.blog.admin.module.post.dto.PageResponse;
import com.yangcong.blog.admin.module.post.dto.PostDetailResponse;
import com.yangcong.blog.admin.module.post.dto.PostListResponse;
import com.yangcong.blog.admin.module.post.dto.UpdatePostRequest;
import com.yangcong.blog.admin.module.post.model.Post;
import com.yangcong.blog.admin.module.post.repository.PostRepository;
import com.yangcong.blog.admin.module.post.service.PostService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private static final String STATUS_DRAFT = "draft";
    private static final String STATUS_PUBLISHED = "published";

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PageResponse<PostListResponse> listPosts(int pageNum, int pageSize, String status) {
        IPage<Post> page = postRepository.findPage(pageNum, pageSize, status);
        List<PostListResponse> records = page.getRecords().stream()
                .map(this::toListResponse)
                .toList();
        return new PageResponse<>(records, page.getTotal(), pageNum, pageSize, page.getPages());
    }

    @Override
    public PostDetailResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "文章不存在"));
        return toDetailResponse(post);
    }

    @Override
    public PostDetailResponse createPost(CreatePostRequest request, Long authorId) {
        Post post = new Post();
        post.setTitle(request.title());
        post.setSummary(request.summary());
        post.setContent(request.content());
        post.setAuthorId(authorId);

        String status = request.status();
        if (status == null || status.isEmpty()) {
            status = STATUS_DRAFT;
        }
        if (!STATUS_DRAFT.equals(status) && !STATUS_PUBLISHED.equals(status)) {
            throw new BusinessException(400, "文章状态只能为 draft 或 published");
        }
        post.setStatus(status);

        if (STATUS_PUBLISHED.equals(status)) {
            post.setPublishedAt(LocalDateTime.now());
        }

        postRepository.insert(post);
        return toDetailResponse(post);
    }

    @Override
    public PostDetailResponse updatePost(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "文章不存在"));

        if (request.title() != null) {
            post.setTitle(request.title());
        }
        if (request.summary() != null) {
            post.setSummary(request.summary());
        }
        if (request.content() != null) {
            post.setContent(request.content());
        }
        if (request.status() != null) {
            String newStatus = request.status();
            if (!STATUS_DRAFT.equals(newStatus) && !STATUS_PUBLISHED.equals(newStatus)) {
                throw new BusinessException(400, "文章状态只能为 draft 或 published");
            }
            if (STATUS_PUBLISHED.equals(newStatus) && !STATUS_PUBLISHED.equals(post.getStatus())) {
                post.setPublishedAt(LocalDateTime.now());
            }
            post.setStatus(newStatus);
        }

        postRepository.updateById(post);
        return toDetailResponse(post);
    }

    @Override
    public void deletePost(Long id) {
        int rows = postRepository.deleteById(id);
        if (rows == 0) {
            throw new BusinessException(404, "文章不存在");
        }
    }

    private PostListResponse toListResponse(Post post) {
        return new PostListResponse(
                post.getId(),
                post.getTitle(),
                post.getSummary(),
                post.getStatus(),
                post.getAuthorId(),
                post.getPublishedAt(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    private PostDetailResponse toDetailResponse(Post post) {
        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getSummary(),
                post.getContent(),
                post.getStatus(),
                post.getAuthorId(),
                post.getPublishedAt(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
