package com.springtraining.furnitureshop.repository.impl;

import com.springtraining.furnitureshop.repository.AvatarRepository;
import com.springtraining.furnitureshop.util.AvatarProps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.when;

@SpringBootTest
class AvatarRepositoryImplTest {
    public static final String FILE_PNG = "file.png";

    @Autowired
    AvatarRepository avatarRepository;
    @Autowired
    AvatarProps props;

    @Mock
    MultipartFile file;

    @BeforeEach
    @AfterEach
    void removeFile() throws IOException {
        Files.deleteIfExists(getPath());
    }

    @Test
    public void shouldSaveNewFile() throws IOException {
        when(file.getBytes()).thenReturn(new byte[]{});
        avatarRepository.save(file, FILE_PNG);
        Assertions.assertTrue(Files.exists(getPath()));
    }

    private Path getPath() {
        return Path.of(props.getDirectory() + FILE_PNG);
    }
}