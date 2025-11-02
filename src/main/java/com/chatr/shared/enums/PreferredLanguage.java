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

//    public static PreferredLanguage fromCode() {
//        for PreferredLanguage language : values()) {
//
//        }
//    }
}
