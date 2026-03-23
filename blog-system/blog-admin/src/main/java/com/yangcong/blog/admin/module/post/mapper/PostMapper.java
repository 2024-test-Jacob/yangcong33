package com.yangcong.blog.admin.module.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangcong.blog.admin.module.post.model.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
