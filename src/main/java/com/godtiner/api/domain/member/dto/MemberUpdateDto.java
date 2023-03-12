package com.godtiner.api.domain.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateDto {

    //private Optional<String> name;
    private Optional<String> nickname;

    private Optional<String> introduction;
   /* @ApiModelProperty(value = "추가된 이미지", notes = "추가된 이미지를 첨부해주세요.")
    private List<MultipartFile> addedImage= new ArrayList<>();
    @ApiModelProperty(value = "제거된 이미지 아이디", notes = "제거된 이미지 아이디를 입력해주세요.")
    private List<Long> deletedImage= new ArrayList<>();

    @ApiModelProperty(value = "이미지", notes = "이미지를 첨부해주세요.")
    private Optional<MultipartFile> uploadImage;*/



}
