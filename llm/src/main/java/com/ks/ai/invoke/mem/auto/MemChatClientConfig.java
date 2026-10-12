package com.ks.ai.invoke.mem.auto;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemChatClientConfig {
    @Autowired
    private JdbcChatMemoryRepository jdbcChatMemoryRepository;

    /**
     * 当引入spring-ai-starter-model-ollama后，由于自动配置，Spring中自动存在一个名为“ollamaChatModel”的ChatModel
     */
    @Bean(name = "memChatClient")
    public ChatClient dsChatClient(@Qualifier("ollamaChatModel") ChatModel chatModel) {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(20) //上下文最多20条记录
                .build();

        ChatClient client = ChatClient.builder(chatModel)
                //添加AOP环绕，在与大模型交互的前后查、写库
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        return client;
    }
}
