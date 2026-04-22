package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.repository.AvatarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AvatarServiceTest {

    private static final String FILE_NAME = "avatar.png";

    @Mock
    private AvatarRepository avatarRepository;

    @InjectMocks
    private AvatarService avatarService;

    @Test
    void save_shouldDelegateToRepository() throws IOException {
        MultipartFile file = mock(MultipartFile.class);

        avatarService.save(file, FILE_NAME);

        verify(avatarRepository, times(1)).save(file, FILE_NAME);
    }
}

