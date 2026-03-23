-- 博客系统 MVP v1 测试数据
-- 使用前请先执行 sql/init.sql
USE blog_system;

-- 示例管理员
-- 密码哈希请在正式接入 Spring Security / BCrypt 后替换
INSERT INTO users (username, password_hash, nickname, status)
VALUES ('admin', '$2a$10$replace_with_real_bcrypt_hash', 'Yangcong', 1)
ON DUPLICATE KEY UPDATE
    nickname = VALUES(nickname),
    status = VALUES(status);

-- 示例文章
INSERT INTO posts (title, summary, content, status, author_id, published_at)
VALUES (
    '我的第一篇博客',
    '这是博客系统 MVP 的第一篇测试文章。',
    '欢迎来到我的动态博客系统，这篇文章用于验证建库建表后的最小闭环。',
    'published',
    1,
    NOW()
);
