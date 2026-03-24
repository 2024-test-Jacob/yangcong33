package com.yangcong.blog.web.module.post.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangcong.blog.web.common.BusinessException;
import com.yangcong.blog.web.module.post.dto.PageResponse;
import com.yangcong.blog.web.module.post.dto.PostDetailResponse;
import com.yangcong.blog.web.module.post.dto.PostListResponse;
import com.yangcong.blog.web.module.post.model.Post;
import com.yangcong.blog.web.module.post.repository.PostRepository;
import com.yangcong.blog.web.module.post.service.PostService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PageResponse<PostListResponse> listPublishedPosts(int pageNum, int pageSize) {
        IPage<Post> page = postRepository.findPublishedPage(pageNum, pageSize);
        List<PostListResponse> records = page.getRecords().stream()
                .map(this::toListResponse)
                .toList();
        return new PageResponse<>(records, page.getTotal(), pageNum, pageSize, page.getPages());
    }

    @Override
    public PostDetailResponse getPublishedPost(Long id) {
        Post post = postRepository.findPublishedById(id)
                .orElseThrow(() -> new BusinessException(404, "文章不存在或未发布"));
        return toDetailResponse(post);
    }

    private PostListResponse toListResponse(Post post) {
        return new PostListResponse(
                post.getId(),
                post.getTitle(),
                post.getSummary(),
                post.getAuthorId(),
                post.getPublishedAt()
        );
    }

    private PostDetailResponse toDetailResponse(Post post) {
        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getSummary(),
                post.getContent(),
                post.getAuthorId(),
                post.getPublishedAt(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
