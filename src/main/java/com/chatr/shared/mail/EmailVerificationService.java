package com.chatr.shared.mail;

public interface EmailVerificationService {
    void cacheVerificationCode(String email, String code);
    boolean verifyCode(String email, String code);
}
