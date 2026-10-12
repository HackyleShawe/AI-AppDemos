package com.ks.ai.invoke.mem.auto;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 使用内存作为记忆的介质，临时测试使用
 */
@RestController
@RequestMapping("temp")
public class TempMemoryController {
    @Autowired
    @Qualifier("tempOllamaMemoryChatClient") ChatClient chatClient;

    @GetMapping(value = "/hi", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> hi(@RequestParam(value = "q", defaultValue = "你是谁") String q) {
        return chatClient.prompt()
                .user(q)
                .stream()
                .content()
                //doOnNext 是 Reactor 提供的一个旁路操作。每当 Flux 产生一个元素，就执行一次指定操作。doOnNext() 不会消费掉数据，也不会改变数据。
                .doOnNext(System.out::println)
                //当前 Flux 完成之后，再追加一个 [complete]。这样前端可以识别数据传输完毕
                .concatWith(Flux.just("[complete]"));
    }
}

@Configuration
class TempChatClient {
    @Bean(name = "tempOllamaMemoryChatClient")
    public ChatClient tempOllamaMemoryChatClient(OllamaChatModel chatModel,
                                                 @Qualifier("tempOllamaChatMemory") ChatMemory chatMemory) {
        return ChatClient.builder(chatModel)
                //添加记忆
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Bean(name = "tempOllamaChatMemory")
    public ChatMemory chatMemory() {
        //使用内存作为记忆
        InMemoryChatMemoryRepository inMemoryChatMemoryRepository = new InMemoryChatMemoryRepository();
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(inMemoryChatMemoryRepository)
                .maxMessages(20) //上下文最多20条记录
                .build();
        return chatMemory;
    }
}
