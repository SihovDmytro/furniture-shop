package com.springtraining.furnitureshop.repository.impl;

import com.springtraining.furnitureshop.repository.AvatarRepository;
import com.springtraining.furnitureshop.util.AvatarProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Repository
@Slf4j
public class AvatarRepositoryImpl implements AvatarRepository {
    private final AvatarProps avatarProps;

    @Autowired
    public AvatarRepositoryImpl(AvatarProps avatarProps) {
        this.avatarProps = avatarProps;
    }

    @Override
    public void save(MultipartFile multipartFile, String name) throws IOException {
        try {
            log.trace("save start");
            Path path = Paths.get(avatarProps.getDirectory() + name);
            log.info("path: " + path);
            Files.write(path, multipartFile.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException exception) {
            log.error("Cannot save avatar.", exception);
        }
    }
}
