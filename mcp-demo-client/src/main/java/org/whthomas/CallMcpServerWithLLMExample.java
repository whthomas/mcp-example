package org.whthomas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClientAsync;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionMessageToolCall;
import com.openai.models.chat.completions.ChatCompletionTool;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CallMcpServerWithLLMExample {

    private final ObjectMapper objectMapper;
    private final McpSyncClient mcpClient;
    private final OpenAIClientAsync openAIClient;

    public void chatWithMcpServer(String userMessage, String chatModel) {

        List<ChatCompletionTool> chatCompletionTools = McpToolExample.prepareChatCompletionTools(mcpClient);

        // 构建ChatCompletionCreateParams对象，设置用户消息、模型、工具等参数
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(userMessage)
                .model(chatModel)
                .tools(chatCompletionTools)
                .build();

        // 调用OpenAI的Chat API
        openAIClient
                .chat()
                .completions()
                .create(params)
                .thenAccept(completion -> completion
                        .choices()
                        .stream()
                        .map(ChatCompletion.Choice::message)
                        .flatMap(message -> {
                            message.content().ifPresent(System.out::println);
                            return message.toolCalls().stream().flatMap(Collection::stream);
                        })
                        .forEach(toolCall -> System.out.println(callFunction(toolCall.function())))
                )
                .join();

    }

    /**
     * 通过MCP Client 调用MCP Server上的方法
     *
     * @param function LLM思考得到的期望被调用的函数信息（包括函数对应的参数）
     * @return
     */
    private String callFunction(ChatCompletionMessageToolCall.Function function) {

        try {

            Map<String, Object> toolParams = objectMapper.readValue(function.arguments(), HashMap.class);

            McpSchema.CallToolResult result = mcpClient.callTool(new McpSchema.CallToolRequest(function.name(), toolParams));

            // 将执行的结果返回
            return result.content().stream().map(Object::toString).collect(Collectors.joining());

        } catch (Exception ex) {
            return ex.getMessage();
        }

    }

}
