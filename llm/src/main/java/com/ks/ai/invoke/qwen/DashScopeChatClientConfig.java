package com.ks.ai.invoke.qwen;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DashScopeChatClientConfig {
    /**
     * 当引入spring-ai-alibaba-starter-dashscope后，由于自动配置，Spring中自动存在一个名为“dashScopeChatClient”的ChatModel
     * 完全等价于：DashScopeChatModel
     */
    @Bean(name = "dashScopeChatClient")
    public ChatClient dsChatClient(@Qualifier("dashScopeChatModel") ChatModel chatModel,
                                   DashScopeChatModel dashScopeChatModel) {
        return ChatClient.builder(chatModel).build();
    }
}
