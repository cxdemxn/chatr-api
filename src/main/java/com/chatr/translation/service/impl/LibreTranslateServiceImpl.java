package com.chatr.translation.service.impl;

import com.chatr.shared.config.LibreTranslateConfig;
import com.chatr.shared.enums.PreferredLanguage;
import com.chatr.translation.dto.LibreTranslateRequestDto;
import com.chatr.translation.dto.LibreTranslateResponseDto;
import com.chatr.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Primary
@Service
@RequiredArgsConstructor
public class LibreTranslateServiceImpl implements TranslationService {

    private static final String TRANSLATE_ENDPOINT = "/translate";
    private final RestTemplate libreTranslateRestTemplate;
    private final LibreTranslateConfig libreTranslateConfig;

    @Override
    public String translate(String text, PreferredLanguage preferredLanguage) {
        LibreTranslateRequestDto libreTranslateRequestDto = new LibreTranslateRequestDto(
                text,
                "auto",
                preferredLanguage.getCode()
        );

        if (!libreTranslateConfig.getApiKey().isEmpty()) {
            libreTranslateRequestDto.setApiKey(libreTranslateConfig.getApiKey());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LibreTranslateRequestDto> entity = new HttpEntity<>(libreTranslateRequestDto, headers);

        String fullUrl = libreTranslateConfig.getLibreTranslateBaseUrl() + TRANSLATE_ENDPOINT;

        LibreTranslateResponseDto libreTranslateResponseDto;
        try {
            libreTranslateResponseDto = libreTranslateRestTemplate.postForObject(
                    fullUrl,
                    entity,
                    LibreTranslateResponseDto.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Error calling LibreTranslate API: " + e.getMessage(), e);
        }

        if (libreTranslateResponseDto != null && libreTranslateResponseDto.getTranslatedText() != null) {
            return libreTranslateResponseDto.getTranslatedText();
        }

        throw new RuntimeException("Translation Failed: recieved null or empty response from LibreTranslate");
    }
}
