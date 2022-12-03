package com.godtiner.api.domain.sharedroutines.dto.sharedRoutines;


import com.godtiner.api.domain.member.dto.MemberInfoDto;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsDto;
import com.godtiner.api.domain.sharedroutines.SharedContents;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.sharedContents.SharedContentsDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value = "공유 루틴 상세 페이지")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedRoutineDetail {

    private Long id;
    private String title;
    private String routine_content;
    private String detailThumbnail;
    private MemberInfoDto member;

    private List<SharedContentsDto> sharedContentsList;


    public static SharedRoutineDetail toDto(SharedRoutines sharedRoutines) {
        return new SharedRoutineDetail(
                sharedRoutines.getId(),
                sharedRoutines.getTitle(),
                sharedRoutines.getRoutineContent(),
                sharedRoutines.getDetail_thumbnail_filename(),
                MemberInfoDto.toDto(sharedRoutines.getWriter()),
                sharedRoutines.getSharedContentsList().stream()
                        .map(SharedContentsDto::new).collect(Collectors.toList())

                //post.getImages().stream().map(i -> ImageDto.toDto(i)).collect(toList())
        );
    }

}
