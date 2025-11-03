package com.chatr.shared.enums;

import java.util.prefs.PreferencesFactory;

public enum PreferredLanguage {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr");

    private final String code;

    PreferredLanguage(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PreferredLanguage fromCode(String code) {
        for (PreferredLanguage language : values()) {
            if (language.code.equalsIgnoreCase(code)) {
                return language;
            }
        }

        throw new IllegalArgumentException("Unknown language code: " + code);
    }

    public static boolean isValid(String code) {
        for (PreferredLanguage language : values()) {
            if (language.code.equalsIgnoreCase(code))
                return true;
        }

        return false;
    }
}
