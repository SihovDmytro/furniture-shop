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

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getAttempts() + 1;
        userRepository.updateAttempts(newFailAttempts, user.getLogin());
    }

    public void resetFailedAttempts(String login) {
        userRepository.updateAttempts(0, login);
    }

    public void ban(User user) {
        Calendar unbanDate = Calendar.getInstance();
        unbanDate.add(Calendar.SECOND, userProps.getBanDuration());
        user.setUnban(unbanDate);
        user.setAttempts(0);
        userRepository.save(user);
    }

    public void unban(User user) {
        user.setUnban(null);
        userRepository.save(user);
    }
}
