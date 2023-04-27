package com.springtraining.furnitureshop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
public interface AvatarRepository {
    void save(MultipartFile multipartFile, String name) throws IOException;
}
