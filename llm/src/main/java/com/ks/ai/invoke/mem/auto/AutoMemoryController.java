package com.ks.ai.invoke.mem.auto;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutoMemoryController {

    @Qualifier("memChatClient")
    @Autowired
    private ChatClient chatClient;

    @GetMapping("/chatMemory")
    public String chatMemory(
            @RequestParam(value = "uid") String userId, // 用户ID
            @RequestParam(value = "cid") String conversationID,// 会话ID
            @RequestParam(value="message",defaultValue = "你是谁？") String message ){
        // 核心：拼接用户ID和会话ID，生成全局唯一的会话标识
        // 格式：用户ID + 分隔符 + 会话ID（分隔符用下划线/竖线，避免和ID本身冲突）
        String uniqueConversationId = userId + "_" + conversationID;

        String result = chatClient.prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, uniqueConversationId))
                .call()
                .content();
        return result;
    }

}
