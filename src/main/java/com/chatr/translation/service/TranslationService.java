package com.chatr.translation.service;

import com.chatr.shared.enums.PreferredLanguage;

public interface TranslationService {
    public String translate(String text, PreferredLanguage preferredLanguage);

    default String translateOrFallback(String text, PreferredLanguage preferredLanguage) {
        try {
            return translate(text, preferredLanguage);
        } catch (Exception e) {
            return text;
        }
    }
}
