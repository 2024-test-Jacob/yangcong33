# blog-server

Spring Boot 后端服务，当前已完成 MVP 第一阶段的登录模块骨架。

## 推荐本地环境

- JDK：17
- Java 编译目标：17
- Maven：3.6.3+
- GroupId：`com.yangcong`
- 父工程目录名：`blog-system`
- 子模块 ArtifactId / 目录名：`blog-server`
- Spring Boot 版本：`3.5.12`

> 版本选择原则：优先确定 JDK，再确定 Spring Boot 主线，最后在该主线内选较新的稳定补丁版本。

## 已实现

- `POST /api/auth/login`
- `GET /api/auth/me`
- 基于 MySQL `users` 表的登录校验
- 使用 Spring Security `PasswordEncoder` 校验密码
- 使用内存 Token 保存登录态（后续可替换为 JWT）

## 启动前准备

1. 先执行仓库根目录下的 `sql/init.sql`
2. 按需执行 `sql/seed.sql`
3. 优先通过环境变量覆盖数据库配置：
   - `DB_URL`
   - `DB_USERNAME`
   - `DB_PASSWORD`
4. 如果你在 IDEA 中直接启动，也可以在 Run Configuration 中配置上述环境变量

> 当前 `sql/seed.sql` 默认写入的是 `{noop}admin123`，方便本地先跑通登录流程；
> 后续接入正式环境时再切换为 `{bcrypt}` 哈希。

## 启动命令

```bash
cd blog-system
mvn -pl blog-server spring-boot:run
```
