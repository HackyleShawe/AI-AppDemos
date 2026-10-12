package com.ks.ai.tool.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("func")
public class ToolController {
    @Qualifier("ollamaChatClient")
    @Autowired
    private ChatClient chatClient;

    @RequestMapping(value = "/hi")
    public String helloOllama(@RequestParam(value = "q", defaultValue = "你是谁")String question) {
        return chatClient.prompt()
                .user(question)
                .call().content();
    }
}

