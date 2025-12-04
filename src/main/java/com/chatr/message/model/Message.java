package com.chatr.message.model;

import com.chatr.shared.model.BaseEntity;
import com.chatr.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String originalText;
    private String translatedText;
    private String languageFrom;
    private String languageTo;
    private LocalDateTime timestamp;
}
