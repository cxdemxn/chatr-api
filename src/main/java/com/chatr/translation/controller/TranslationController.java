package com.chatr.translation.controller;

import com.chatr.shared.enums.PreferredLanguage;
import com.chatr.translation.dto.TranslationRequestDto;
import com.chatr.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/translations")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    @PostMapping
    public ResponseEntity<String> translateText(@RequestBody TranslationRequestDto request) {
        // Validate input
        if (request.getText() == null || request.getTargetLanguage() == null) {
            return ResponseEntity.badRequest().body("Text and Target Language are required.");
        }

        // Call the service (which uses OpenAI)
        String translatedText = translationService.translate(
                request.getText(),
                request.getTargetLanguage()
        );

        return ResponseEntity.ok(translatedText);
    }
}