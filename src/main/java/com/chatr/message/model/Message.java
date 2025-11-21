package com.chatr.message.model;

import com.chatr.shared.enums.PreferredLanguage;
import com.chatr.shared.model.BaseEntity;
import com.chatr.user.model.User;
import jakarta.persistence.*;

@Entity
public class Message extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String originalText;
    private String translatedText;

    @Enumerated(EnumType.STRING)
    private PreferredLanguage sourceLanguage;

    @Enumerated(EnumType.STRING)
    private PreferredLanguage targetLanguage;

    private boolean read;
}

