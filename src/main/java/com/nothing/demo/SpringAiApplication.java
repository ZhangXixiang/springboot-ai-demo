package com.nothing.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringAiApplication implements ApplicationRunner {


    @Value("${server.port}")
    private Integer port;

    @Value("${spring.profiles.active}")
    private String env;

    public static void main(String[] args) {
        SpringApplication.run(SpringAiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("\n==================================================\n" + env + "环境" + " 启动成功\n"
                + "本地访问地址：http://localhost:" + port + "\n" + "swagger地址：http://localhost:" + port + "/doc.html#/\n"
                + "==================================================");
    }

}
