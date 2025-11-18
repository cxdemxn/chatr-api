package com.chatr.mail.controller;

import com.chatr.mail.service.EmailService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-sign-up-code")
    private ResponseEntity<Map<String, Object>> sendSignUpCode(@NotNull @RequestBody Map<String, String> body) {

        String email = body.get("email");
        emailService.sendSignUpCode(email);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "Successful");
        responseBody.put("message", "Email sent successfully");

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/verify-sign-up")
    private ResponseEntity<Map<String, Object>> verifySignUpEmail(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String code = body.get("code");

        Map<String, Object> responseBody = new HashMap<>();

        if (emailService.verifySignUpEmail(email, code)) {
            responseBody.put("status", "Successful");
            responseBody.put("message", "Email verified successfully");
            return ResponseEntity.ok(responseBody);
        }

        responseBody.put("status", "Failed");
        responseBody.put("message", "Invalid email or code");
        return ResponseEntity.badRequest().body(responseBody);
    }
}
