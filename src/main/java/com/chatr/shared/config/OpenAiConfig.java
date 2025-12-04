package com.chatr.shared.config;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientImpl;
import com.openai.client.okhttp.OkHttpClient;
import com.openai.core.ClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api-key}")
    private String apiKey;

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .timeout(Duration.ofSeconds(30))
                .build();
    }

    @Bean
    public OpenAIClient openAIClient(OkHttpClient okHttpClient) {
        return new OpenAIClientImpl(
                ClientOptions.builder()
                        .apiKey(apiKey)
                        .httpClient(okHttpClient)
                        .build()
        );
    }
}