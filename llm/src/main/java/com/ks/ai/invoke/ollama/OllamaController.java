package com.ks.ai.invoke.ollama;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/ollama")
@RestController
public class OllamaController {

    @Qualifier("ollamaChatClient")
    @Autowired
    private ChatClient chatClient;

    /**
     * 流式输出：Flux实现【推荐】
     * 注意：加了produces = MediaType.TEXT_EVENT_STREAM_VALUE
     *   响应类型Content-Type: text/event-stream，返回的数据会以 SSE 流的形式持续发送。
     *   前端才会更好的接收数据
     */
    @RequestMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> hello(@RequestParam(value = "q", defaultValue = "你是谁")String question) {
        return chatClient.prompt()
                // AI 能力边界
                .system("你是一个法律助手，只回答法律问题，其它问题回复，我只能回答法律相关问题，其它无可奉告")
                .user(question)
                .stream()
                .content()
                //doOnNext 是 Reactor 提供的一个旁路操作。每当 Flux 产生一个元素，就执行一次指定操作。doOnNext() 不会消费掉数据，也不会改变数据。
                .doOnNext(System.out::println)
                //当前 Flux 完成之后，再追加一个 [complete]。这样前端可以识别数据传输完毕
                .concatWith(Flux.just("[complete]"));
    }

    /**
     * 流式输出：SseEmitter实现
     * produces = MediaType.TEXT_EVENT_STREAM_VALUE 以 SSE 的形式持续返回给客户端。
     */
    @RequestMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sse(@RequestParam(value = "q", defaultValue = "你是谁")String question) {
        // 1. 创建SSE发射器，设置超时时间
        SseEmitter emitter = new SseEmitter(60 * 1000L);
        CompletableFuture.runAsync(() -> {
            try {
                chatClient
                        .prompt()
                        .user(question)
                        .stream()
                        .content()
                        .doOnNext(content -> {
                            try {
                                emitter.send(SseEmitter.event().data(content));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .doOnComplete(emitter::complete)
                        .doOnError(emitter::completeWithError)
                        .blockLast();

            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    /**
     * 提示词模板
     */
    @GetMapping("/prompttemplate/chattxt")
    public Flux<String> chat3(@RequestParam("candidateName")String candidateName, @RequestParam("jobPosition")String jobPosition,
                              @RequestParam("entryDate")String entryDate, @RequestParam("salaryRange")String salaryRange,
                              @RequestParam("welfare")String welfare,
                              // System模板的动态变量：企业名称、Offer类型
                              @RequestParam("companyName")String companyName, @RequestParam("offerType")String offerType) throws IOException {

        // ========== 1. System提示词模板（包含占位符） ==========
        // 加载resources/prompts/下的模板文件
        ClassPathResource systemTemplateFile = new ClassPathResource("prompts/system.txt");
        // 读取文件内容（指定UTF-8编码，避免中文乱码）
        String systemTemplateStr = new String(
                systemTemplateFile.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        // 2. 创建System模板对象，填充System侧的变量
        PromptTemplate systemPromptTemplate = new PromptTemplate(systemTemplateStr);
        Map<String, Object> systemVariables = Map.of(
                "companyName", companyName,  // 企业名称（动态）
                "offerType", offerType      // Offer类型（如"正式员工"/"实习生"）
        );
        // 渲染System模板，得到填充后的完整System提示词
        String systemContent = systemPromptTemplate.render(systemVariables);
        // 生成SystemMessage
        SystemMessage systemMessage = new SystemMessage(systemContent);

        // ========== 2. User提示词模板（原有逻辑不变） ==========
        ClassPathResource userTemplateFile = new ClassPathResource("prompts/user.txt");
        String userTemplateStr = new String(
                userTemplateFile.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
        PromptTemplate userPromptTemplate = new PromptTemplate(userTemplateStr);
        Map<String, Object> userVariables = Map.of(
                "candidateName", candidateName,
                "jobPosition", jobPosition,
                "entryDate", entryDate,
                "salaryRange", salaryRange,
                "welfare", welfare
        );
        String userContent = userPromptTemplate.render(userVariables);
        UserMessage userMessage = new UserMessage(userContent);

        // ========== 3. 组合消息并调用大模型 ==========
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        // 4. 调用大模型，获取结果
        return chatClient.prompt(prompt)
                .stream()
                .content()
                //doOnNext 是 Reactor 提供的一个旁路操作。每当 Flux 产生一个元素，就执行一次指定操作。doOnNext() 不会消费掉数据，也不会改变数据。
                .doOnNext(System.out::println)
                //当前 Flux 完成之后，再追加一个 [complete]。这样前端可以识别数据传输完毕
                .concatWith(Flux.just("[complete]"));
    }





}
