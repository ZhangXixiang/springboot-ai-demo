package com.nothing.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * 接入spring-ai
 *
 * @author Tiger
 */
@Slf4j
@RestController
@RequestMapping("/openai/api/chat")
@Tag(name = "Openai-AI聊天接口")
public class OpenAiChatController {

    private final ChatClient chatClient;

    @Autowired
    public OpenAiChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * 流式返回
     *
     * @param message message
     * @return res
     */
    @Operation(summary = "OpenAi流式对话", description = "返回SSE格式的流式响应")
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam(value = "message", defaultValue = "Tell me a story") String message) {
        Flux<String> content = chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .doOnNext(data -> log.info("Streaming data: {}", data)); // 逐条打印内容
        return content;
    }


    /**
     * 正常返回
     *
     * @param message message
     * @return res
     */
    @Operation(summary = "OpenAi普通对话", description = "返回单次响应")
    @GetMapping("/normal")
    public String normal(@RequestParam(value = "message", defaultValue = "Tell me a story") String message) {
        return chatClient.prompt(message).call().content();
    }


}