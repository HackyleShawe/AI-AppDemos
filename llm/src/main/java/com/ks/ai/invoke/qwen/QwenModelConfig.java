package com.ks.ai.invoke.qwen;

//import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
//import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
//import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 手动创建千问模型访问客户端，包含ChatModel和ChatClient
 */
@Configuration
public class QwenModelConfig {
    //@Value("${llm.qwen.v3.2.apiKey}")
    //private String qwenApiKey;
    //@Value("${llm.qwen.v3.2.baseUrl}")
    //private String qwenBaseUrl;
    //
    //@Bean(name = "qwen")
    //public ChatModel qwen() {
    //    return DashScopeChatModel.builder()
    //            .dashScopeApi(
    //                    DashScopeApi.builder()
    //                            .apiKey(System.getenv(qwenApiKey))
    //                            .baseUrl(qwenBaseUrl)
    //                            .build()
    //            )
    //            .defaultOptions(
    //                    DashScopeChatOptions.builder()
    //                            .withModel("qwen3-max")
    //                            .build()
    //            )
    //            .build();
    //}
    //
    //@Bean(name = "qwen")
    //public ChatClient qwenChatClient(@Qualifier("qwen") ChatModel dashscopeChatModel){
    //    return ChatClient.builder(dashscopeChatModel).build();
    //}
}
