package com.chatr.translation.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LibreTranslateResponseDto {
    private final String translatedText;
}
