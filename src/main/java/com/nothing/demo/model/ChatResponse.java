package com.nothing.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "聊天响应")
public class ChatResponse {
    @Schema(description = "响应内容")
    private String content;

    @Schema(description = "请求ID")
    private String requestId;

    @Schema(description = "时间戳")
    private long timestamp;
    
    // 用于流式响应
    public static ChatResponse of(String content) {
        ChatResponse response = new ChatResponse();
        response.setContent(content);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
} 