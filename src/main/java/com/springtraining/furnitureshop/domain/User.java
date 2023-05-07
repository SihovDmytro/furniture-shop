package com.springtraining.furnitureshop.domain;


import com.springtraining.furnitureshop.util.DateUtil;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements UserDetails {
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

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "send_mail", nullable = false)
    private boolean sendMail;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "attempts", nullable = false)
    private int attempts;

    @Column(name = "unban")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar unban;

    @Column(length = 25, nullable = false)
    private String avatar;

    public User() {
    }

    public User(String login, String name, String surname, String password, String email, boolean sendMail, Role role, int attempts, Calendar unban, String avatar) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.sendMail = sendMail;
        this.role = role;
        this.attempts = attempts;
        this.unban = unban;
        this.avatar = avatar;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return getUnban() == null || getUnban().before(Calendar.getInstance());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum Role {
        ADMIN, USER
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSendMail() {
        return sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Calendar getUnban() {
        return unban;
    }

    public void setUnban(Calendar unban) {
        this.unban = unban;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!Objects.equals(login, user.login)) return false;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", sendMail=" + sendMail +
                ", role=" + role +
                ", attempts=" + attempts +
                ", unban=" + DateUtil.dateToString(unban, Locale.getDefault()) +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
