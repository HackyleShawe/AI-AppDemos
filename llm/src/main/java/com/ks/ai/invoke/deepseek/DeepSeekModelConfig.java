package com.ks.ai.invoke.deepseek;

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
 * 手动创建deepseek模型访问客户端，包含ChatModel和ChatClient
 */
@Configuration
public class DeepSeekModelConfig {
    //@Value("${llm.deepseek.v3.2.apiKey}")
    //private String dsApiKey;
    //@Value("${llm.deepseek.v3.2.baseUrl}")
    //private String dsBaseUrl;
    //
    //@Bean(name = "deepseek")
    //public ChatModel deepseek() {
    //    return DashScopeChatModel.builder()
    //            .dashScopeApi(
    //                    DashScopeApi.builder()
    //                            .apiKey(System.getenv(dsApiKey))
    //                            .baseUrl(dsBaseUrl)
    //                            .build()
    //            )
    //            .defaultOptions(
    //                    DashScopeChatOptions.builder()
    //                            .withModel("deepseek-v3.2")
    //                            .build()
    //            )
    //            .build();
    //}
    //
    //@Bean(name = "deepseek")
    //public ChatClient dsChatClient(@Qualifier("deepseek") ChatModel dashscopeChatModel){
    //    return ChatClient.builder(dashscopeChatModel).build();
    //}

}
