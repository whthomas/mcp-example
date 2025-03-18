package org.whthomas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClientAsync;
import com.openai.client.okhttp.OpenAIOkHttpClientAsync;
import com.openai.models.chat.completions.ChatCompletionMessageToolCall;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CallMcpServerWithLLMExample {

    private ObjectMapper objectMapper = new ObjectMapper();

    private String callFunction(McpSyncClient mcpClient, ChatCompletionMessageToolCall.Function function) {

        try {

            Map<String, Object> toolParams = objectMapper.readValue(function.arguments(), HashMap.class);

            McpSchema.CallToolResult result = mcpClient.callTool(new McpSchema.CallToolRequest(function.name(), toolParams));

            return result.content().stream().map(Object::toString).collect(Collectors.joining());

        } catch (Exception ex) {
            return ex.getMessage();
        }

    }

    public OpenAIClientAsync initOpenAIClient(String url, String apiKey) {

        return OpenAIOkHttpClientAsync.builder()
                .baseUrl(url)
                .apiKey(apiKey)
                .build();

    }

}
