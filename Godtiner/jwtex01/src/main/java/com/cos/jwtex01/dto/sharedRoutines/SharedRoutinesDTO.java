package com.cos.jwtex01.dto.sharedRoutines;

import com.cos.jwtex01.entity.RoutineTags;
import com.cos.jwtex01.entity.SharedRoutines;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SharedRoutinesDTO {

    private Long id;
    private String title;
    private Long writerId;
    private String writerNickname;
    private String content;


    //이미지
    private String thumbnailUrl;
    private String stored_filename;
    private Long filesize;

    private String original_filename;

    private LocalDateTime regDate,modDate;
    private List<RoutineTagsDTO> tagList;
    private List<SharedContentsDTO> sharedContentsList;

    public SharedRoutinesDTO(SharedRoutines sharedRoutines) {
        this.id =sharedRoutines.getId();
        this.title = sharedRoutines.getTitle();
        this.writerId = sharedRoutines.getWriter().getId();
        this.writerNickname = sharedRoutines.getWriter().getNickname();
        this.tagList = sharedRoutines.getTagList().stream().map(RoutineTagsDTO::new).collect(Collectors.toList());
        this.regDate =sharedRoutines.getRegDate();
        this.thumbnailUrl = sharedRoutines.getThumbnailUrl();
        this.filesize = sharedRoutines.getFilesize();
        this.stored_filename = sharedRoutines.getStored_filename();
        this.original_filename = sharedRoutines.getOriginalFileName();
        this.sharedContentsList = sharedRoutines.getSharedContentsList().stream()
                .map(SharedContentsDTO::new).collect(Collectors.toList());

    }

    @Getter
    public static class RoutineTagsDTO {
        private String tag;
        private int idx;

        public RoutineTagsDTO(RoutineTags routineTags){
            this.idx = routineTags.getIdx();
            this.tag = routineTags.getTag();
        }
    }

}



