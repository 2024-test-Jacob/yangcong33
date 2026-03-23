package com.yangcong.blog;

import com.yangcong.blog.module.auth.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
                + "com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration"
})
class BlogServerApplicationTests {

    @MockBean
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }
}
