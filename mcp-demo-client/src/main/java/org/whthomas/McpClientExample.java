package org.whthomas;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.ClientMcpTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.time.Duration;

public class McpClientExample {

    /**
     * 创建一个Fetch MCP Client
     *
     * @return
     */
    public static McpSyncClient createFetchMcpClient() {

        // 声明MCP Server的参数
        ServerParameters params = ServerParameters
                .builder("node")
                .args("/Users/whthomas/Documents/Cline/MCP/fetch-mcp/dist/index.js")
                .build();

        // 声明MCP Server的连接方式
        ClientMcpTransport transport = new StdioClientTransport(params);

        // 创建一个MCP Client
        McpSyncClient client = McpClient
                .sync(transport)
                .requestTimeout(Duration.ofSeconds(10))
                // 设置MCP Client的基本信息
                .clientInfo(new McpSchema.Implementation("Fetch", "1.0.0"))
                .capabilities(McpSchema.ClientCapabilities.builder().roots(true).sampling().build())
                .build();

        // 初始化MCP Server
        client.initialize();

        return client;
    }

}
