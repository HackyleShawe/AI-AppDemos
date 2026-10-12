package com.ks.ai.invoke.deepseek;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.ai.model.deepseek.autoconfigure.DeepSeekChatProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeepSeekChatClientConfig {
    /**
     * 当引入spring-ai-starter-model-deepseek后，由于自动配置，Spring中自动存在一个DeepSeekChatModel
     */
    @Bean(name = "deepSeekChatClient")
    public ChatClient dsChatClient(DeepSeekChatModel chatModel){
        return ChatClient.builder(chatModel).build();
    }

    /**
     * 自定义模型连接参数
     * @DeepSeekChatProperties Deepseek在yml文件中的配置
     */
    //@Bean(name = "deepSeekCustomChatClient")
    public ChatClient deepSeekCustomChatClient(DeepSeekChatProperties chatProperties){

        //从环境变量中获取API_KEY
        DeepSeekApi deepSeekApi = DeepSeekApi.builder()
                .apiKey(System.getenv("DEEP_SEEK_KEY"))
                .build();

        DeepSeekChatModel deepSeekChatModel = DeepSeekChatModel.builder()
                .deepSeekApi(deepSeekApi)
                //SpringAI 1.x是defaultOptions，2.x是options
                .defaultOptions(DeepSeekChatOptions.builder()
                        .model(DeepSeekApi.ChatModel.DEEPSEEK_REASONER).build()) //指定推理模型
                .build();

        return ChatClient.builder(deepSeekChatModel).build();
    }
}
