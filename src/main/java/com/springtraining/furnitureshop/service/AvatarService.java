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

    public void save(MultipartFile multipartFile, String name) throws IOException {
        avatarRepository.save(multipartFile, name);
    }
}
