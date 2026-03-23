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
- 当前业务代码放在子模块 `blog-server` 中

## 当前建议版本

- Spring Boot：`3.5.12`
- JDK：`17`
- Maven：`3.6.3+`

## IDEA 提示

如果你的父工程目录下还额外出现了一个顶层 `src/`，而你又不打算让父工程自己成为一个可运行模块，
那么这个顶层 `src/` 一般可以删除或忽略。

换句话说：

- `blog-system/src/`：通常不需要
- `blog-system/blog-server/src/`：这是当前真正应该保留的代码目录
