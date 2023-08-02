package com.pocekt.art.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;


@Configuration
@Slf4j
@PropertySource("classpath:application.properties")
public class ChatGPTConfig{
    @Value("${gpt.token}")
    private String token;

    @Bean
    public OpenAiService openAiService(){
        log.info("apiToken:{}을 활용한 OpenAiService",token);
        return new OpenAiService(token, Duration.ofSeconds(60));
    }
}