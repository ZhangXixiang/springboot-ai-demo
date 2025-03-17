package com.nothing.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 接入spring-ai
 *
 * @author Tiger
 */
@Slf4j
@RestController
@RequestMapping("/openai/api/chat")
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
    @GetMapping("/normal")
    public String normal(@RequestParam(value = "message", defaultValue = "Tell me a story") String message) {
        return chatClient.prompt(message).call().content();
    }


}