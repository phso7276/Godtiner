package com.godtiner.api.domain.member.service;

import com.godtiner.api.global.file.FileException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {


    String save(MultipartFile multipartFile) throws FileException;

    void delete(String filePath);
}