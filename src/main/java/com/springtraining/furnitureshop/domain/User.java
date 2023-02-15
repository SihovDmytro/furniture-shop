package com.springtraining.furnitureshop.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "user")
public class User {
    @GeneratedValue(generator = "ID_GENERATOR")
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "login", nullable = false, length = 20)
    private String login;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "surname", nullable = false, length = 30)
    private String surname;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "sendMail", nullable = false)
    private boolean sendMail;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "attempts", nullable = false)
    private int attempts;

    @Column(name = "unban", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar unban;

    public enum Role {
        ADMIN, USER
    }

    public User(String login, String name, String surname, String password, String email, boolean sendMail, Role role, int attempts, Calendar unban) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.sendMail = sendMail;
        this.role = role;
        this.attempts = attempts;
        this.unban = unban;
    }
}
