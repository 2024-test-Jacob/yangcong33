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
        ├── mapper/            # MyBatis / MyBatis-Plus 映射层
        ├── model/             # 当前模块的数据模型
        ├── repository/        # 面向业务的数据访问层
        ├── security/          # 登录态、拦截器等安全相关能力
        └── service/           # 业务接口层
            └── impl/          # 业务实现层
```

### 你关心的几个概念

- `controller`
  - 负责接收 HTTP 请求，例如 `/api/auth/login`
- `service`
  - 负责定义业务接口，让上层依赖抽象
- `service/impl`
  - 负责具体业务实现，例如校验用户名密码、生成 token
- `repository`
  - 负责面向业务提供数据访问能力
- `mapper`
  - 负责底层 ORM / SQL 映射能力
- `dto`
  - 负责接口入参和出参对象
- `model`
  - 负责表示当前模块用到的数据结构
- `security`
  - 负责登录态、鉴权拦截、当前用户上下文

### `repository` 和 `mapper` 到底有什么区别？

现在我们已经调整成：**保留 `repository`，同时引入 `mapper`**。

#### 当前为什么这样分层

因为我们现在的数据访问方式已经切换为：

- Spring Boot
- MyBatis-Plus
- `mapper + repository`

这种写法下：

- `mapper` 负责更底层的 ORM / SQL 映射
- `repository` 负责面向业务提供更稳定的数据访问语义

这也是很多企业项目中的常见做法。

#### `mapper` 一般在什么场景更常见

`mapper` 这个命名更常见于：

- MyBatis
- MyBatis-Plus

例如后面如果你引入：

- `UserMapper.java`
- `@Mapper`
- XML SQL 或注解 SQL

那时 `mapper` 就会更贴切。

#### 现在的结论

现在已经不是“空建 mapper 层”，而是**真正使用 MyBatis-Plus 引入了 mapper 层**。

当前结构建议理解为：

1. `mapper`
   - 更贴近 ORM / SQL 映射实现
2. `repository`
   - 更贴近业务层可理解的数据访问接口

#### 这样做对我们当前项目的好处

1. 后续如果你换 SQL 写法，优先改 `mapper`
2. 上层 `service/impl` 不一定要直接感知 ORM 细节
3. 未来如果模块继续增大，`repository` 会比直接到处注入 `mapper` 更利于收口

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

## 关于 `service` 要不要拆接口

当前已经调整为：**`service` 放接口，`service/impl` 放实现类**。

这种写法并不是“过时才这么做”，而是企业 Java 项目里依然非常常见的一种分层方式，优势通常在于：

1. controller 面向接口编程，更利于解耦
2. 后续如果要补充不同实现、做测试替身或扩展能力，更容易演进
3. 目录职责更清晰：接口定义和实现代码分开

当然，如果是非常小的项目，也有人会直接省略接口层、只保留一个 service 类，以减少样板代码。

所以更准确的理解不是“新旧之分”，而是：**看项目规模、团队习惯和后续演进需求来取舍**。当前这个项目为了更贴近常见企业项目写法，采用接口 + 实现类的结构。
