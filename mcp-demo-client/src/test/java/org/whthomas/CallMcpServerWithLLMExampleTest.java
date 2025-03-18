package org.whthomas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.okhttp.OpenAIOkHttpClientAsync;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;

class CallMcpServerWithLLMExampleTest {
    private ConfigReader configReader;

    @BeforeEach
    void setUp() throws IOException {
        configReader = new ConfigReader("src/main/resources/mcp-config.properties");
    }

    @Test
    void chatWithMcpServer() {

        String apiEndpoint = configReader.getProperty("api.endpoint");
        String apiKey = configReader.getProperty("api.key");
        String chatModel = configReader.getProperty("api.model");

        CallMcpServerWithLLMExample callMcpServerWithLLMExample = new CallMcpServerWithLLMExample(
                new ObjectMapper(),
                McpClientExample.createFetchMcpClient(),
                OpenAIOkHttpClientAsync
                        .builder()
                        .baseUrl(apiEndpoint)
                        .apiKey(apiKey)
                        .build()
        );

        callMcpServerWithLLMExample.chatWithMcpServer("I want to view https://www.example.com HTML content", chatModel);

    }

}
