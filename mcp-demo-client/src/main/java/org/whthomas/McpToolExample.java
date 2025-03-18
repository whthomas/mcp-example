package org.whthomas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.core.JsonValue;
import com.openai.models.FunctionDefinition;
import com.openai.models.FunctionParameters;
import com.openai.models.chat.completions.ChatCompletionTool;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.Tool;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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

    public static List<ChatCompletionTool> prepareChatCompletionTools(McpSyncClient mcpClient) {

        // List available tools
        McpSchema.ListToolsResult tools = mcpClient.listTools();

        return tools
                .tools()
                .stream()
                .map(tool -> {

                    return ChatCompletionTool
                            .builder()
                            .function(FunctionDefinition.builder()
                                    .name(tool.name())
                                    .description(tool.description())
                                    .parameters(prepareFunctionParameters(tool))
                                    .build()
                            )
                            .build();

                })
                .toList();

    }

    private static FunctionParameters prepareFunctionParameters(Tool tool) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            String jsonString = objectMapper.writeValueAsString(tool.inputSchema());

            JsonNode jsonNode = objectMapper.readTree(jsonString);

            FunctionParameters.Builder paramsBuilder = FunctionParameters.builder();

            jsonNode.fields().forEachRemaining(entry -> {

                JsonValue value = JsonValue.fromJsonNode(entry.getValue());

                paramsBuilder.putAdditionalProperty(entry.getKey(), value);

            });

            return paramsBuilder.build();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
