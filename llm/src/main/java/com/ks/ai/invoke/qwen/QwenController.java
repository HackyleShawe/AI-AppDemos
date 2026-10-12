package com.ks.ai.invoke.qwen;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class QwenController {
    @Qualifier("dashScopeChatModel")
    @Autowired
    private ChatModel chatModel;

    @RequestMapping("/qwen")
    public Flux<String> helloQwen(@RequestParam(value = "q", defaultValue = "你是谁")String question) {
        return chatModel.stream(question);
    }

    @Autowired
    DashScopeChatModel dashScopeChatModel;
    @RequestMapping("/qwen1")
    public Flux<String> helloQwen1(@RequestParam(value = "q", defaultValue = "你是谁")String question) {
        return dashScopeChatModel.stream(question);
    }

    //@Qualifier("dashScopeChatClient")
    //@Autowired
    //private ChatClient chatClient;
    //@RequestMapping("/qwen2")
    //public Flux<String> helloQwen2(@RequestParam(value = "q", defaultValue = "你是谁")String question) {
    //    return chatClient.stream(question);
    //}

}
