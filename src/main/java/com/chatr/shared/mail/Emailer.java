package com.chatr.shared.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Emailer {

    private final JavaMailSender mailer;

    public void sendPlainText(String to, String from, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setText(body);
        message.setSubject(subject);

        mailer.send(message);
    }

    public void sendHtml(String to, String from, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = mailer.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setText(htmlBody, true);
        mimeMessageHelper.setSubject(subject);

        mailer.send(mimeMessage);
    }

}
