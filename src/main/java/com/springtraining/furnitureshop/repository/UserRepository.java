package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Modifying
    @Query("UPDATE User u SET u.attempts=?1 WHERE u.login=?2")
    void updateAttempts(int attempts, String login);
}
