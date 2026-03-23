# blog-server

Spring Boot 后端服务，当前已完成 MVP 第一阶段的登录模块骨架。

## 已实现

- `POST /api/auth/login`
- `GET /api/auth/me`
- 基于 MySQL `users` 表的登录校验
- 使用 Spring Security `PasswordEncoder` 校验密码
- 使用内存 Token 保存登录态（后续可替换为 JWT）

## 启动前准备

1. 先执行仓库根目录下的 `sql/init.sql`
2. 按需执行 `sql/seed.sql`
3. 修改 `src/main/resources/application.yml` 中的数据库账号密码

> 当前 `sql/seed.sql` 默认写入的是 `{noop}admin123`，方便本地先跑通登录流程；
> 后续接入正式环境时再切换为 `{bcrypt}` 哈希。

## 启动命令

```bash
mvn spring-boot:run
```
