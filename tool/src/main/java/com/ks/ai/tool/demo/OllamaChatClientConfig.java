package com.ks.ai.tool.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaChatClientConfig {
    @Autowired
    private ToolDemo toolDemo;

    /**
     * 当引入spring-ai-starter-model-ollama后，由于自动配置，Spring中自动存在一个名为“ollamaChatModel”的ChatModel
     * 完全等价于OllamaChatModel
     */
    @Bean(name = "ollamaChatClient")
    public ChatClient ollamaChatClient(OllamaChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultTools(toolDemo)
                .build();
    }
}
