package com.chatr.translation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LibreTranslateRequestDto {
    private final String q;
    private final String source;
    private final String target;
    private final String format = "text";
    private String apiKey;
}
