package com.chatr.translation.dto;

import com.chatr.shared.enums.PreferredLanguage;
import lombok.Data;

@Data
public class TranslationRequestDto {
    private String text;
    private PreferredLanguage targetLanguage;
}
