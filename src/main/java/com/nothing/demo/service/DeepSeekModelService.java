package com.nothing.demo.service;

import com.nothing.demo.config.DeepSeekModelConfig;
import com.nothing.demo.model.ChatRequest;
import com.nothing.demo.model.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
@Service
public class DeepSeekModelService {
    private final WebClient webClient;
    private final DeepSeekModelConfig deepSeekModelConfig;

    public DeepSeekModelService(DeepSeekModelConfig deepSeekModelConfig) {
        this.deepSeekModelConfig = deepSeekModelConfig;
        this.webClient = WebClient.builder()
                .baseUrl(deepSeekModelConfig.getApiUrl())
                .build();
    }

    public Flux<ChatResponse> streamChat(String message) {
        ChatRequest request = buildChatRequest(message);
        
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(String.class)
                .map(response -> {
                    log.debug("Received response: {}", response);
                    return ChatResponse.of(response);
                })
                .doOnNext(data -> log.info("Streaming data: {}", data)) // 逐条打印内容
                .doOnError(error -> log.error("Error in stream chat", error));
    }

    private ChatRequest buildChatRequest(String message) {
        ChatRequest.PromptContent promptContent = new ChatRequest.PromptContent();
        promptContent.setNormal("你是一个助手，请根据已知信息，简洁和专业的来回答问题。请严格按照已知信息进行推理和回复，即保证答案和已知信息的一致性，不允许在答案中添加编造成分，答案中不允许篡改已知信息中的专业名词，如果无法从中得到答案，请说 '我没有找到有关内容，或许您可以换一种方式提问，我将尽力帮助您'，答案请使用中文。最后加入答案出自那个条例");
        promptContent.setAbnormal("你是一助手，不需要你自己生成内容，你只需要说 \"我没有找到有关内容，或许您可以换一种方式提问，我将尽力帮助您\"，不允许在答案中添加编造成分，答案请使用中文");

        return ChatRequest.builder()
                .query(message)
                .temperature(0.7)
                .stream(true)
                .history(null)
                .model_code("deepseek-r1:8b-llama-distill-fp16")
                .top_k(8)
                .score_threshold(0.5)
                .prompt_content(promptContent)
                .knowledge_base_names(Arrays.asList(
                    "3fc4eba3-8c1b-4dd6-a5a3-be11f0ad5434",
                    "9db0751d-d9f1-41eb-bedf-34fd94111e9f",
                    "f594a67a-5668-4b34-9a9f-2d660642f229",
                    "f2ad0df6-f72a-42e1-8b7c-170f11949a3f",
                    "65464b7e-ae08-4973-ab3c-69cd77778d05",
                    "c5e1dfd3-cd10-4943-98e1-eb1cfb3b4d16",
                    "a3fe72ab-148b-40dc-a881-c63837e73ad6"
                ))
                .through_information(Arrays.asList("73317835-d4be-4676-ba86-04105a876fc5"))
                .doc_urls(Arrays.asList())
                .build();
    }
} 