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

## 当前目录结构说明

现在把登录相关代码集中到了 `module/auth` 下面，按“功能模块”而不是“全局分散包”来组织：

```text
src/main/java/com/yangcong/blog/
├── BlogServerApplication.java
├── common/                    # 全局通用能力
├── config/                    # 全局配置
└── module/
    └── auth/
        ├── controller/        # 接口入口层
        ├── dto/               # 请求/响应对象
        ├── model/             # 当前模块的数据模型
        ├── repository/        # 数据访问层
        ├── security/          # 登录态、拦截器等安全相关能力
        └── service/           # 业务逻辑层
```

### 你关心的几个概念

- `controller`
  - 负责接收 HTTP 请求，例如 `/api/auth/login`
- `service`
  - 负责写业务逻辑，例如校验用户名密码、生成 token
- `repository`
  - 负责和数据库打交道，例如查询 `users` 表
- `dto`
  - 负责接口入参和出参对象
- `model`
  - 负责表示当前模块用到的数据结构
- `security`
  - 负责登录态、鉴权拦截、当前用户上下文

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
