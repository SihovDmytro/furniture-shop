package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    /**
     * Persists the given multipart file as an avatar image under the configured directory.
     *
     * @param multipartFile the uploaded file to save; must not be {@code null}
     * @param name          the target file name (including extension)
     * @throws IOException if writing the file to disk fails
     */
    public void save(MultipartFile multipartFile, String name) throws IOException {
        avatarRepository.save(multipartFile, name);
    }
}
