package com.yangcong.blog.module.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangcong.blog.module.auth.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
