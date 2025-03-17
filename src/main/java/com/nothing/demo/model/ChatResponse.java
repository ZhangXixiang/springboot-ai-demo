package com.nothing.demo.model;

import lombok.Data;

@Data
public class ChatResponse {
    private String content;
    private String requestId;
    private long timestamp;
    
    // 用于流式响应
    public static ChatResponse of(String content) {
        ChatResponse response = new ChatResponse();
        response.setContent(content);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
} 