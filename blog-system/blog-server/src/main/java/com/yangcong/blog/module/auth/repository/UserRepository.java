package com.yangcong.blog.module.auth.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yangcong.blog.module.auth.mapper.UserMapper;
import com.yangcong.blog.module.auth.model.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * 当前阶段的数据访问层。
 *
 * <p>当前项目已经引入 MyBatis-Plus，但仍保留 repository 作为“面向业务的数据访问入口”。
 * repository 负责对上提供稳定语义，mapper 负责更底层的 ORM/SQL 映射能力。</p>
 */
@Repository
public class UserRepository {

    private final UserMapper userMapper;

    public UserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Optional<User> findByUsername(String username) {
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, username)
                        .last("LIMIT 1")
        );
        return Optional.ofNullable(user);
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userMapper.selectById(id));
    }
}
