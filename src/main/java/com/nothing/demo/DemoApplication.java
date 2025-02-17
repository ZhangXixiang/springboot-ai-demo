package com.nothing.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    /**
     * official demo
     *
     * @param builder builder
     * @return res
     */
    @Bean
    public CommandLineRunner runner(ChatClient.Builder builder) {
        return args -> {
            ChatClient chatClient = builder.build();

            //            ChatClient.StreamResponseSpec onePlusOneEqualsWhat = chatClient.prompt("one plus one equals what").stream();
            //            System.out.println(onePlusOneEqualsWhat);

            String response = chatClient.prompt("Tell me a joke").call().content();
            System.out.println(response);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
