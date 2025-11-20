package com.chatr.mail.service;

import com.chatr.shared.exceptions.UserAlreadyExistsException;
import com.chatr.shared.mail.EmailVerificationServiceImpl;
import com.chatr.shared.mail.Emailer;
import com.chatr.shared.utils.EmailUtil;
import com.chatr.user.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final EmailUtil emailUtil;
    private final Emailer emailer;
    private final EmailVerificationServiceImpl emailVerificationService;
    private final UserRepository userRepository;

    public void sendSignUpCode(@NotNull String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        String code = emailUtil.generateSignUpCode();
        emailVerificationService.cacheVerificationCode(email, code);
        emailer.sendPlainText(email, "noreply@chatr.dev", "Email Verification", "registration code is: " + code);

//        may need to design html template for emails
    }

    public boolean verifySignUpEmail(String email, String code) {
        return emailVerificationService.verifyCode(email, code);
    }
}
