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
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        //이미지 파일만 업로드 가능
        if(Objects.requireNonNull(multipartFile.getContentType()).startsWith("image")==false){
            log.warn("this file is not image type");

        }
       // List<String> filenames = new ArrayList<>();

        String ext = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

        String imageFileName= UUID.randomUUID()+"."+ext;

        String filePath = location + imageFileName;
        Path savePath = Paths.get(filePath);

        log.info("savePath"+savePath);
        //filenames.add(imageFileName);

        try {

            //multipartFile.transferTo(new File(filePath));
            multipartFile.transferTo(savePath);
            //썸네일 생성
            String FeedThumbnail = location+"s_"+imageFileName;
            String DetailThumbnail = location+"d_"+imageFileName;

            //filenames.add(FeedThumbnail);
            //filenames.add(DetailThumbnail);

            File FeedThumbnailFile = new File(FeedThumbnail);
            File DetailThumbnailFile= new File(DetailThumbnail);

            Thumbnailator.createThumbnail(savePath.toFile(),FeedThumbnailFile, 170,114);
            Thumbnailator.createThumbnail(savePath.toFile(),DetailThumbnailFile, 288,176);

        }catch (IOException e){
            e.printStackTrace();
            //파일 저장 에러!
            throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
        }


        return imageFileName;
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

    @Override
    public String profileSave(MultipartFile multipartFile) {
        //이미지 파일만 업로드 가능
        if(Objects.requireNonNull(multipartFile.getContentType()).startsWith("image")==false){
            log.warn("this file is not image type");
        }

        String ext = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);


        String imageFileName= UUID.randomUUID()+"."+ext;
        String filePath = location+File.separator +imageFileName;
        Path savePath = Paths.get(filePath);

        try {

            //multipartFile.transferTo(new File(filePath));
            multipartFile.transferTo(savePath);
            //썸네일 생성


        }catch (IOException e){
            e.printStackTrace();
            //파일 저장 에러!
            throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
        }


        return imageFileName;
    }
}

