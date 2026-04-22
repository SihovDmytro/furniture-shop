package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.repository.UserRepository;
import com.springtraining.furnitureshop.util.UserProps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final long USER_ID = 1L;
    private static final String TEST_LOGIN = "testLogin";
    private static final int BAN_DURATION = 60;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProps userProps;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(TEST_LOGIN, "name", "surname", "password", "email@test.com",
                false, User.Role.USER, 0, null, "");
    }

    @Test
    void getUser_shouldDelegateToRepository() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUser(USER_ID);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(USER_ID);
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void deleteUser_shouldDelegateToRepository() {
        userService.deleteUser(user);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void getUserByLogin_shouldDelegateToRepository() {
        when(userRepository.findByLogin(TEST_LOGIN)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByLogin(TEST_LOGIN);

        assertTrue(result.isPresent());
        verify(userRepository, times(1)).findByLogin(TEST_LOGIN);
    }

    @Test
    void addUser_shouldSaveUser() {
        userService.addUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void increaseFailedAttempts_shouldIncrementByOne() {
        user = new User(TEST_LOGIN, "name", "surname", "password", "email@test.com",
                false, User.Role.USER, 2, null, "");

        userService.increaseFailedAttempts(user);

        verify(userRepository, times(1)).updateAttempts(3, TEST_LOGIN);
    }

    @Test
    void resetFailedAttempts_shouldSetAttemptsToZero() {
        userService.resetFailedAttempts(TEST_LOGIN);

        verify(userRepository, times(1)).updateAttempts(0, TEST_LOGIN);
    }

    @Test
    void ban_shouldSetUnbanDateAndZeroAttempts() {
        when(userProps.getBanDuration()).thenReturn(BAN_DURATION);
        user = new User(TEST_LOGIN, "name", "surname", "password", "email@test.com",
                false, User.Role.USER, 3, null, "");

        userService.ban(user);

        assertNotNull(user.getUnban());
        assertEquals(0, user.getAttempts());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void unban_shouldClearUnbanDate() {
        userService.unban(user);

        assertNull(user.getUnban());
        verify(userRepository, times(1)).save(user);
    }
}


