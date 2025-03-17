package com.nothing.demo.controller;

import com.nothing.demo.model.ChatResponse;
import com.nothing.demo.service.DeepSeekModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/deepseek/api/chat")
public class DeepSeekChatController {
    
    private final DeepSeekModelService deepSeekModelService;

    public DeepSeekChatController(DeepSeekModelService deepSeekModelService) {
        this.deepSeekModelService = deepSeekModelService;
    }

    /**
     * 流式对话接口
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> streamChat(@RequestParam String message) {
        log.info("Received streaming chat request: {}", message);
        return deepSeekModelService.streamChat(message);
    }

    /**
     * 普通对话接口
     */
    @PostMapping("/completion")
    public ChatResponse chat(@RequestParam String message) {
        log.info("Received chat request: {}", message);
        
        // 这里替换为实际调用你们公司模型的代码
        // return modelService.generate(message);
        
        ChatResponse response = new ChatResponse();
        response.setContent("这是来自自定义模型的响应: " + message);
        response.setRequestId(String.valueOf(System.currentTimeMillis()));
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
} 