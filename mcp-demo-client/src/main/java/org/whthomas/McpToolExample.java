package org.whthomas;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.ClientMcpTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.time.Duration;
import java.util.Map;

public class McpToolExample {

    /**
     * 调用MCP工具并执行，打印结果
     *
     * @param client MCP Client
     */
    public void callMcpTools(McpSyncClient client, String toolName, Map<String, Object> toolParams) {

        McpSchema.CallToolResult result = client.callTool(new McpSchema.CallToolRequest(toolName, toolParams));

        result.content().forEach(System.out::println);

    }

    /**
     * 列出所有MCP工具并打印
     *
     * @param client MCP Client
     */
    public void listMcpTools(McpSyncClient client) {

        // 列出所有MCP工具
        McpSchema.ListToolsResult tools = client.listTools();

        tools.tools().forEach(tool -> {

            String template = """
                    tool: %s
                    description: %s
                    schema: %s
                    """.formatted(
                    tool.name(), tool.description(), tool.inputSchema()
            );

            System.out.println(template);

        });

    }

}
