# MCP 演示客户端

这是一个使用 Model Context Protocol (MCP) 的 Java 客户端示例项目。

## 项目结构

```
mcp-demo-client/
├── src/
│   ├── main/
│   │   ├── java/org/whthomas/
│   │   │   ├── CallMcpServerWithLLMExample.java
│   │   │   ├── ConfigReader.java
│   │   │   ├── McpClientExample.java
│   │   │   └── McpToolExample.java
│   │   └── resources/
│   │       └── mcp-config.properties.example
│   └── test/
│       └── java/org/whthomas/
│           └── CallMcpServerWithLLMExampleTest.java
└── pom.xml
```

## 快速开始

1. 克隆仓库
2. 复制 `mcp-config.properties.example` 为 `mcp-config.properties` 并配置
3. 运行 `mvn clean install` 构建项目
4. 运行示例代码：
   - `McpClientExample`：基本 MCP 客户端示例
   - `McpToolExample`：MCP 工具使用示例
   - `CallMcpServerWithLLMExample`：通过 LLM 调用 MCP 服务器示例
5. 运行测试观察示例：
   - 执行 `mvn test` 运行单元测试
   - 重点关注 `CallMcpServerWithLLMExampleTest#chatWithMcpServer` 方法，这是观察 MCP 服务器与 LLM 交互的主要入口

## 依赖

- Java 17+
- Maven 3.8+

## 配置

在 `mcp-config.properties` 中配置 MCP 服务器连接信息。

## 测试

运行 `mvn test` 执行单元测试。
