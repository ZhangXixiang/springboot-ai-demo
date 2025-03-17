package com.nothing.demo.model;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class ChatRequest {
    private String query;
    private double temperature;
    private boolean stream;
    private Object history;
    private String model_code;
    private int top_k;
    private double score_threshold;
    private PromptContent prompt_content;
    private List<String> knowledge_base_names;
    private List<String> through_information;
    private List<String> doc_urls;

    @Data
    public static class PromptContent {
        private String normal;
        private String abnormal;
    }
} 