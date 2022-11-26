package com.godtiner.api.domain.member.service;


import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.global.exception.FileUploadFailureException;
import com.godtiner.api.global.file.FileException;
import com.godtiner.api.global.file.FileExceptionType;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@Log4j2
public class LocalFileService implements FileService {

    @Value("${upload.image.location}")
    private String location; // 1

    private final MemberRepository memberRepository;

    public LocalFileService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public String save(MultipartFile multipartFile)  {
        String ext = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

        String filePath = location+ UUID.randomUUID()+"."+ext;

        try {

            multipartFile.transferTo(new File(filePath));
        }catch (IOException e){
            //파일 저장 에러!
            throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
        }


        return filePath;
    }
    @PostConstruct
    void postConstruct() { // 2
        log.info("location:"+location);
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }



    @Override
    public void delete(String path) {
        File file = new File(path);

        //존재하지 않는데 굳이 지우나..?
        if(!file.exists()) return;

        if(!file.delete()) throw new FileException(FileExceptionType.FILE_CAN_NOT_DELETE);

    }
    }

