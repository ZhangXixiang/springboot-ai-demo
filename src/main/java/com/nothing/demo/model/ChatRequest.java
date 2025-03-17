package com.nothing.demo.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "聊天请求参数")
public class ChatRequest {
    @Schema(description = "用户问题", required = true)
    private String query;

    @Schema(description = "温度参数", example = "0.7")
    private double temperature;

    @Schema(description = "是否流式返回", example = "true")
    private boolean stream;

    @Schema(description = "历史对话")
    private Object history;

    @Schema(description = "模型代码", example = "deepseek-r1:8b-llama-distill-fp16")
    private String model_code;

    @Schema(description = "top k参数", example = "8")
    private int top_k;

    @Schema(description = "分数阈值", example = "0.5")
    private double score_threshold;

    @Schema(description = "提示词内容")
    private PromptContent prompt_content;

    @Schema(description = "知识库名称列表")
    private List<String> knowledge_base_names;

    @Schema(description = "通过信息列表")
    private List<String> through_information;

    @Schema(description = "文档URL列表")
    private List<String> doc_urls;

    @Data
    @Schema(description = "提示词内容")
    public static class PromptContent {
        @Schema(description = "正常提示词")
        private String normal;

        @Schema(description = "异常提示词")
        private String abnormal;
    }
} 