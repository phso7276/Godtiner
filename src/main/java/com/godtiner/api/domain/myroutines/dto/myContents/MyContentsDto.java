package com.godtiner.api.domain.myroutines.dto.myContents;

import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutineRules;
import com.godtiner.api.domain.myroutines.dto.myRules.MyRoutineRulesDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MyContentsDto {
    private Long routineId;
    private String content;
    //루틴 순번
    private int idx;

    //시작 시간
    private LocalTime startTime;
    //종료시간
    private LocalTime endTime;
    private List<MyRoutineRulesDto> myRules;

    public MyContentsDto(MyContents myContents){
        this.routineId = myContents.getMyRoutines().getId();
        this.content =myContents.getContent();
        this.idx=myContents.getIdx();
        this.startTime = myContents.getStartTime();
        this.endTime =myContents.getEndTime();
        this.myRules =myContents.getMyRoutineRulesList().stream()
                .map(MyRoutineRulesDto::new).collect(Collectors.toList());

    }
}
