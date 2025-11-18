package com.chatr.mail.service;

import com.chatr.shared.mail.EmailVerificationServiceImpl;
import com.chatr.shared.mail.Emailer;
import com.chatr.shared.utils.EmailUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final EmailUtil emailUtil;
    private final Emailer emailer;
    private final EmailVerificationServiceImpl emailVerificationService;

    public void sendSignUpCode(@NotNull String email) {
        String code = emailUtil.generateSignUpCode();
        emailVerificationService.cacheVerificationCode(email, code);
        emailer.sendPlainText(email, "noreply@chatr.dev", "Email Verification", "registration code is: " + code);

//        may need to design html template for emails
    }

    public boolean verifySignUpEmail(String email, String code) {
        return emailVerificationService.verifyCode(email, code);
    }
}
