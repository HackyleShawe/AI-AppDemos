package com.ks.ai.agent.mcp.server.client.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaChatClientConfig {


    @Bean(name = "ollamaChatClient")
    public ChatClient dsChatClient(OllamaChatModel ollamaChatModel,
                                   ToolCallbackProvider mcpTools) {
        return ChatClient.builder(ollamaChatModel)
                .defaultTools(mcpTools)
                .build();
    }

}
