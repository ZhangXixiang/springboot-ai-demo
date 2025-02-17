package com.nothing.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 接入spring-ai
 *
 * @author Tiger
 */
@RestController
public class StreamingChatController {

    private final ChatClient chatClient;

    @Autowired
    public StreamingChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * 流式返回
     *
     * @param message message
     * @return res
     */
    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam(value = "message", defaultValue = "Tell me a story") String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
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