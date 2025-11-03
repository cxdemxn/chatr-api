package com.chatr.user.model;

import com.chatr.shared.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String email;
    private String password;
    private String preferredLanguage;
}
