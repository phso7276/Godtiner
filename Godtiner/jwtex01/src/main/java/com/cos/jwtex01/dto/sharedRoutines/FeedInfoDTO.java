package com.cos.jwtex01.dto.sharedRoutines;

import com.cos.jwtex01.entity.SharedRoutines;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedInfoDTO {

    private Long id;
    private String title;

    //이미지
    private String thumbnailUrl;
    private String stored_filename;
    private Long filesize;

    private List<SharedRoutinesDTO.RoutineTagsDTO> tagList;

    private int hits;

    //찜하기
    private int likecnt;

    //스크랩
    private int pickcnt;


    public FeedInfoDTO(SharedRoutines sharedRoutines) {
        this.id =sharedRoutines.getId();
        this.title = sharedRoutines.getTitle();
        this.tagList = sharedRoutines.getTagList().stream().map(SharedRoutinesDTO.RoutineTagsDTO::new).collect(Collectors.toList());
        this.thumbnailUrl = sharedRoutines.getThumbnailUrl();
        this.filesize = sharedRoutines.getFilesize();
        this.hits =sharedRoutines.getHits();
        this.likecnt =sharedRoutines.getLikecnt();
        this.pickcnt =sharedRoutines.getPickcnt();
        this.stored_filename = sharedRoutines.getStored_filename();

    }
}
