package com.cos.jwtex01.dto.sharedRoutines;

import com.cos.jwtex01.entity.SharedRoutineRules;
import com.cos.jwtex01.entity.SharedContents;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SharedContentsDTO {

    private String detailcontent;

    //루틴 순번
    private int idx;

    //시작 시간
    private LocalTime startTime;
    //종료시간
    private LocalTime endTime;


    private List<RoutineRulesDTO> routineRules;

    //private SharedRoutines sharedRoutines;

    public SharedContentsDTO(SharedContents sharedContents) {
        this.detailcontent =sharedContents.getContent();
        this.idx =sharedContents.getIdx();
        this.startTime=sharedContents.getStartTime();
        this.endTime =sharedContents.getEndTime();
        this.routineRules =sharedContents.getSharedRoutineRules().stream()
                .map(RoutineRulesDTO::new).collect(Collectors.toList());
    }

    @Getter
    public static class RoutineRulesDTO {
        private boolean mon;
        private boolean tue;
        private boolean wed;
        private boolean thu;
        private boolean fri;
        private boolean sat;
        private boolean sun;

        public RoutineRulesDTO(SharedRoutineRules sharedRoutineRules){
            this.mon= sharedRoutineRules.isMon();
            this.tue= sharedRoutineRules.isTue();
            this.wed= sharedRoutineRules.isWed();
            this.thu = sharedRoutineRules.isThu();
            this.fri= sharedRoutineRules.isFri();
            this.sat= sharedRoutineRules.isSat();
            this.sun= sharedRoutineRules.isSun();
        }
    }
}
