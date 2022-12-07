package com.godtiner.api.domain.sharedroutines.dto.sharedContents;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.godtiner.api.domain.sharedroutines.SharedContents;
import com.godtiner.api.domain.sharedroutines.SharedRoutineRules;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedContentsDto {
    private long id;

    private Long SharedRoutineId;

    private String content;

    //루틴 순번
    private int idx;

    //시작 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm", timezone = "Asia/Seoul")
    private LocalTime startTime;
    //종료시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm", timezone = "Asia/Seoul")
    private LocalTime endTime;

    private List<SharedRoutineRulesDto> sharedRoutineRules;

    public SharedContentsDto(SharedContents sharedContents) {
        this.id=sharedContents.getId();
        this.SharedRoutineId =sharedContents.getSharedRoutine().getId();
        this.content =sharedContents.getContent();
        this.idx =sharedContents.getIdx();
        this.startTime=sharedContents.getStartTime();
        this.endTime =sharedContents.getEndTime();
        this.sharedRoutineRules =sharedContents.getSharedRoutineRules().stream()
                .map(SharedRoutineRulesDto::new).collect(Collectors.toList());
    }
}
