-- 博客系统 MVP v1 初始化脚本
-- 技术栈假设：Spring Boot + MySQL + Vue
-- 说明：
-- 1. 当前阶段不使用外键约束，降低 MVP 阶段的维护和迁移复杂度。
-- 2. 关联关系由应用层保证，例如 posts.author_id 对应 users.id。
-- 3. 建议数据库字符集统一使用 utf8mb4。

CREATE DATABASE IF NOT EXISTS blog_system
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE blog_system;

-- 管理员用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL COMMENT '登录用户名',
    password_hash VARCHAR(255) NOT NULL COMMENT '加密密码',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用，0禁用',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_users_username (username),
    KEY idx_users_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员用户表';

-- 博客文章表
CREATE TABLE IF NOT EXISTS posts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    title VARCHAR(200) NOT NULL COMMENT '文章标题',
    summary VARCHAR(500) DEFAULT NULL COMMENT '文章摘要',
    content LONGTEXT NOT NULL COMMENT '文章正文',
    status VARCHAR(20) NOT NULL DEFAULT 'draft' COMMENT '状态：draft/published',
    author_id BIGINT NOT NULL COMMENT '作者ID，由应用层保证关联 users.id',
    published_at DATETIME DEFAULT NULL COMMENT '发布时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_posts_status (status),
    KEY idx_posts_author_id (author_id),
    KEY idx_posts_created_at (created_at),
    KEY idx_posts_published_at (published_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='博客文章表';
