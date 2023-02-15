package com.springtraining.furnitureshop.service;


import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.repository.UserRepository;
import com.springtraining.furnitureshop.util.UserProps;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserProps userProps;

    public UserService(UserRepository userRepository, UserProps userProps) {
        this.userRepository = userRepository;
        this.userProps = userProps;
    }

    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public boolean login(String login, String password) {
        Optional<User> userOptional = userRepository.findByLogin(login);
        User user = userOptional.orElse(null);
        if (user == null) {
            return false;
        }

        if (isUserBanned(user)) {
            return false;
        } else {
            user.setUnban(null);
        }

        boolean result = true;
        if (!user.getPassword().equals(password)) {
            int attempts = user.getAttempts();
            if (++attempts >= userProps.getMaxLoginAttempts()) {
                Calendar unbanDate = Calendar.getInstance();
                unbanDate.add(Calendar.SECOND, userProps.getBanDuration());
                user.setUnban(unbanDate);
                user.setAttempts(0);
            } else {
                user.setAttempts(attempts);
            }
            result = false;
        }
        userRepository.save(user);
        return result;
    }

    public boolean isUserBanned(User user) {
        return user != null &&
                user.getUnban() != null &&
                user.getUnban().after(Calendar.getInstance());

    }
}
