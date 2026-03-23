-- 博客系统 MVP v1 测试数据
-- 使用前请先执行 sql/init.sql
USE blog_system;

-- 示例管理员
-- 当前使用 {noop}admin123 作为开发期默认密码，便于先跑通登录流程
-- 后续接入正式环境时建议替换为 {bcrypt} 开头的真实哈希
INSERT INTO users (username, password_hash, nickname, status)
VALUES ('admin', '{noop}admin123', 'Yangcong', 1)
ON DUPLICATE KEY UPDATE
    password_hash = VALUES(password_hash),
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
