# 博客系统 MVP v1 数据库设计

## 设计目标

当前阶段目标是先跑通最小闭环：

1. 管理员登录后台。
2. 创建或编辑文章。
3. 将文章保存到 MySQL。
4. 前台读取已发布文章列表与详情。

因此本阶段只保留两张核心表：

- `users`
- `posts`

## 关于外键的取舍

MVP v1 暂不使用数据库外键约束，采用“应用层维护关联关系”的策略。

### 原因

1. 项目还小，表关系简单。
2. 便于后续快速调整表结构。
3. 降低开发早期迁移、重构和导入测试数据的阻力。

### 当前约定

- `posts.author_id` 逻辑上关联 `users.id`
- 该关系由后端服务保证，不由数据库外键强制保证

## 表结构摘要

### users

- `id`: 主键
- `username`: 登录用户名，唯一
- `password_hash`: 加密密码
- `nickname`: 昵称
- `status`: 账号状态
- `created_at`: 创建时间
- `updated_at`: 更新时间

### posts

- `id`: 主键
- `title`: 标题
- `summary`: 摘要
- `content`: 正文
- `status`: `draft` / `published`
- `author_id`: 作者 ID
- `published_at`: 发布时间
- `created_at`: 创建时间
- `updated_at`: 更新时间

## 推荐初始化顺序

1. 执行 `sql/init.sql`
2. 执行 `sql/seed.sql`
3. 本地开发环境可直接使用默认账号 `admin / admin123`
4. 后续接入正式环境时，再将 `password_hash` 切换为更安全的 `{bcrypt}` 哈希
