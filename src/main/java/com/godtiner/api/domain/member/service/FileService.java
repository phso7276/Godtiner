package com.godtiner.api.domain.member.service;

import com.godtiner.api.global.file.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {


    String save(MultipartFile multipartFile) throws FileException;

    void delete(String filePath);

    public String profileSave(MultipartFile multipartFile);
}