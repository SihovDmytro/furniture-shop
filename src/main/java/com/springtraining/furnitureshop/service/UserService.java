package com.springtraining.furnitureshop.service;


import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.repository.UserRepository;
import com.springtraining.furnitureshop.util.UserProps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserProps userProps;

    /**
     * Constructs the service with its required dependencies.
     *
     * @param userRepository repository for persisting and querying {@link User} entities
     * @param userProps      configuration properties for user-related limits (e.g. max login attempts, ban duration)
     */
    public UserService(UserRepository userRepository, UserProps userProps) {
        this.userRepository = userRepository;
        this.userProps = userProps;
    }

    /**
     * Retrieves a user by its primary key.
     *
     * @param id the database ID of the user to look up
     * @return an {@link Optional} containing the matching {@link User}, or empty if not found
     */
    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    /**
     * Returns all users in the system.
     *
     * @return a {@link List} of all {@link User} entities
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Deletes the given user from the database.
     *
     * @param user the user to delete; must not be {@code null}
     */
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     * Retrieves a user by their login name.
     *
     * @param login the unique login of the user to look up
     * @return an {@link Optional} containing the matching {@link User}, or empty if not found
     */
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    /**
     * Persists a new user to the database.
     *
     * @param user the user to save; must not be {@code null}
     */
    public void addUser(User user) {
        userRepository.save(user);
    }

    /**
     * Increments the failed-login attempt counter for the given user by one.
     *
     * @param user the user whose attempt counter should be incremented; must not be {@code null}
     */
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getAttempts() + 1;
        userRepository.updateAttempts(newFailAttempts, user.getLogin());
    }

    /**
     * Resets the failed-login attempt counter for the user with the given login to zero.
     *
     * @param login the login of the user whose attempts should be reset
     */
    public void resetFailedAttempts(String login) {
        userRepository.updateAttempts(0, login);
    }

    /**
     * Bans the given user by setting an unban timestamp based on the configured ban duration,
     * and resets their attempt counter to zero.
     *
     * @param user the user to ban; must not be {@code null}
     */
    public void ban(User user) {
        Calendar unbanDate = Calendar.getInstance();
        unbanDate.add(Calendar.SECOND, userProps.getBanDuration());
        user.setUnban(unbanDate);
        user.setAttempts(0);
        userRepository.save(user);
    }

    /**
     * Unbans the given user by clearing their unban timestamp.
     *
     * @param user the user to unban; must not be {@code null}
     */
    public void unban(User user) {
        user.setUnban(null);
        userRepository.save(user);
    }
}
