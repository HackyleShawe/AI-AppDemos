package com.ks.ai.invoke.mem;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("ai_chat_memory")
@Data
public class AIChatMemory {
    private Long id;
    private String userId;
    private String conversationId;
    private String messageType;
    private String content;
    private LocalDateTime createTime;
}
