package com.godtiner.api.domain.sharedroutines.dto.sharedRoutines;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.RoutineTagDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class SharedRoutinesSimple {

    private Long id;
    private String title;

    private int hits;

    //찜하기
    private int likecnt;

    //스크랩
    private int pickcnt;
    //이미지
    private String thumbnailUrl;
    //private String stored_filename;
    //private Long filesize;

    //private List<SharedRoutinesDto.RoutineTagsDTO> tagList;

    private List<RoutineTagDto> routineTagList;



    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    public SharedRoutinesSimple(SharedRoutines sharedRoutines) {
        this.id =sharedRoutines.getId();
        this.title = sharedRoutines.getTitle();
        this.hits =sharedRoutines.getHits();
        this.likecnt =sharedRoutines.getLikecnt();
        this.pickcnt =sharedRoutines.getPickcnt();
        this.thumbnailUrl = sharedRoutines.getThumbnailUrl();
        this.routineTagList = sharedRoutines.getRoutineTags().stream().map(RoutineTagDto::new).collect(Collectors.toList());
        this.regDate=sharedRoutines.getRegDate();

    }


   /* public SharedRoutinesSimple(SharedRoutines sharedRoutines) {
        this.id =sharedRoutines.getId();
        this.title = sharedRoutines.getTitle();
        this.routineTagList = sharedRoutines.getRoutineTags().stream().map(RoutineTagDto::new).collect(Collectors.toList());
        this.thumbnailUrl = sharedRoutines.getThumbnailUrl();
        this.filesize = sharedRoutines.getFilesize();
        this.hits =sharedRoutines.getHits();
        this.likecnt =sharedRoutines.getLikecnt();
        this.pickcnt =sharedRoutines.getPickcnt();
        this.stored_filename = sharedRoutines.getStored_filename();
        this.createdAt=sharedRoutines.getRegDate();

    }*/
}
