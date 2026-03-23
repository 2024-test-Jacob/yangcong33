package com.yangcong.blog.admin.module.post.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangcong.blog.admin.module.post.model.Post;
import com.yangcong.blog.admin.module.post.mapper.PostMapper;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {

    private final PostMapper postMapper;

    public PostRepository(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public IPage<Post> findPage(int pageNum, int pageSize, String status) {
        Page<Post> page = new Page<>(pageNum, pageSize);
        return postMapper.selectPage(page,
                Wrappers.<Post>lambdaQuery()
                        .eq(status != null && !status.isEmpty(), Post::getStatus, status)
                        .orderByDesc(Post::getCreatedAt));
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(postMapper.selectById(id));
    }

    public void insert(Post post) {
        postMapper.insert(post);
    }

    public void updateById(Post post) {
        postMapper.updateById(post);
    }

    public int deleteById(Long id) {
        return postMapper.deleteById(id);
    }
}
