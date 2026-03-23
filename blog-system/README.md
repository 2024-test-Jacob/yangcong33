# blog-system

这是博客系统的父工程（Aggregator / Parent POM）。

## 推荐目录结构

```text
blog-system/
├── pom.xml                # 父工程，只做统一版本管理与模块聚合
└── blog-server/           # 当前后端子模块
    ├── pom.xml
    └── src/
```

## 结构说明

- 父工程 `blog-system` 负责：
  - `dependencyManagement`
  - 模块聚合
  - 统一 Java / Spring Boot 版本
  - 统一 Maven 插件管理（例如编译插件、Spring Boot 插件）
- 当前业务代码放在子模块 `blog-server` 中

## 父工程 POM 当前负责的内容

当前 `blog-system/pom.xml` 主要负责 4 件事：

1. **声明模块**
   - 当前包含 `blog-server`
2. **继承 Spring Boot 官方父 POM**
   - 即 `spring-boot-starter-parent:3.5.12`
3. **统一属性**
   - `java.version=17`
4. **统一依赖管理**
   - 在 `dependencyManagement` 中集中声明当前项目常用依赖坐标
   - 当前也显式写出了版本属性，便于学习和人工检查
5. **统一构建插件管理**
   - 统一 `maven-compiler-plugin`
   - 统一 `spring-boot-maven-plugin`

这样做的意义是：

- 子模块不需要到处重复写版本
- 后续新增模块时更容易保持一致
- 以后如果升级 Spring Boot，只需要优先调整父工程

### 当前显式版本属性

- `spring-boot.version=3.5.12`
- `spring-security.version=6.5.7`
- `mysql-connector.version=8.0.33`
- `lombok.version=1.18.38`

> 提醒：这种“显式版本号”写法更直观，但后续升级时要注意和 Spring Boot 3.5.12 所兼容的依赖版本保持同步。

## 两层 parent 的理解方式

你现在的项目实际上有两层“父”：

1. **Spring Boot 官方父 POM**
   - `spring-boot-starter-parent`
2. **你自己的业务父工程**
   - `blog-system`

然后：

- `blog-system` 继承 `spring-boot-starter-parent`
- `blog-server` 再继承 `blog-system`

这是一种很常见、也很适合当前项目的写法。

## 当前建议版本

- Spring Boot：`3.5.12`
- JDK：`17`
- Maven：`3.6.3+`

## 版本选择建议

如果你后面还会继续纠结 Spring Boot 版本，建议按下面顺序判断：

1. **先定 JDK 基线**
   - 当前项目建议固定在 `JDK 17`
2. **再选 Spring Boot 主线**
   - 如果你希望继续使用 Java 17，并尽量保持生态成熟度，当前优先沿用 `3.5.x`
3. **在主线内优先选较新的稳定补丁版本**
   - 比如当前使用 `3.5.12`
   - 一般不优先使用 `3.5.0` 这类刚起步的 `.0` 版本
4. **最后确认 Maven 版本是否满足最低要求**
   - 当前建议 `3.6.3+`

这样做的好处是：

- 更容易避免后期大范围版本回退
- 更容易和 Spring 生态里的其他依赖保持兼容
- 更适合在项目初期尽早稳定技术基线

## IDEA 提示

如果你的父工程目录下还额外出现了一个顶层 `src/`，而你又不打算让父工程自己成为一个可运行模块，
那么这个顶层 `src/` 一般可以删除或忽略。

换句话说：

- `blog-system/src/`：通常不需要
- `blog-system/blog-server/src/`：这是当前真正应该保留的代码目录
