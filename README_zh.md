# mcp-demo-client 项目简介

## 项目背景
mcp-demo-client 是一个用于演示如何使用 MCP 服务器的示例项目。

## 技术栈
- 编程语言: Java
- 其他技术: Model Context Protocol (MCP)

## 安装和运行
1. 克隆项目：`git clone https://github.com/your-repo/mcp-demo-client.git`
2. 导入项目到 IDE（如 IntelliJ IDEA 或 Eclipse）。
3. 配置 MCP 服务器：请参阅 `src/main/resources/mcp-config.properties.example` 文件。
4. 构建项目：`mvn clean install`
5. 运行项目：`mvn exec:java -Dexec.mainClass="org.whthomas.McpClientExample"`

## 贡献指南
如果您想为项目做出贡献，请遵循以下步骤：
1. 克隆项目。
2. 创建一个新分支：`git checkout -b feature-branch`
3. 提交更改：`git commit -am "Add feature"`
4. 推送更改：`git push origin feature-branch`
5. 创建 Pull Request。

## 许可证
本项目使用 MIT 许可证。
[MIT License](https://opensource.org/licenses/MIT)
