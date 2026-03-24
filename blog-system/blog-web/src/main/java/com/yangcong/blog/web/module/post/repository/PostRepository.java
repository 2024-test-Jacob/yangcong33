package com.yangcong.blog.web.module.post.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangcong.blog.web.module.post.mapper.PostMapper;
import com.yangcong.blog.web.module.post.model.Post;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {

    private static final String STATUS_PUBLISHED = "published";

    private final PostMapper postMapper;

    public PostRepository(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public IPage<Post> findPublishedPage(int pageNum, int pageSize) {
        Page<Post> page = new Page<>(pageNum, pageSize);
        return postMapper.selectPage(page,
                Wrappers.<Post>lambdaQuery()
                        .eq(Post::getStatus, STATUS_PUBLISHED)
                        .orderByDesc(Post::getPublishedAt));
    }

    public Optional<Post> findPublishedById(Long id) {
        Post post = postMapper.selectOne(
                Wrappers.<Post>lambdaQuery()
                        .eq(Post::getId, id)
                        .eq(Post::getStatus, STATUS_PUBLISHED)
                        .last("LIMIT 1"));
        return Optional.ofNullable(post);
    }
}
