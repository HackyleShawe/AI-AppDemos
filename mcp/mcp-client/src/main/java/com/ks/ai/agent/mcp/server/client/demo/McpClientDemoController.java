package com.ks.ai.agent.mcp.server.client.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class McpClientDemoController {

    @Qualifier("ollamaChatClient")
    @Autowired
    private ChatClient chatClient;

    @RequestMapping(value = "/hi", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> hi() {
        return chatClient
                .prompt()
                .user("北京天气怎么样")
                .stream()
                .content()
                .doOnNext(System.out::println)
                //当前 Flux 完成之后，再追加一个 [complete]。这样前端可以识别数据传输完毕
                .concatWith(Flux.just("[complete]"));
    }
}
