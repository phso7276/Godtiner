package com.godtiner.api.domain.myroutines.dto.myRules;


import com.godtiner.api.domain.myroutines.MyRoutineRules;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyRoutineRulesDto {

    private Long contentsId;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;
    private LocalDateTime regDate;

    public MyRoutineRulesDto(MyRoutineRules myRoutineRules) {
        this.contentsId = myRoutineRules.getMyContentsId().getId();
        this.mon=myRoutineRules.isMon();
        this.tue=myRoutineRules.isTue();
        this.wed=myRoutineRules.isWed();
        this.thu=myRoutineRules.isThu();
        this.fri=myRoutineRules.isFri();
        this.sat=myRoutineRules.isSat();
        this.sun=myRoutineRules.isSun();
        this.regDate=myRoutineRules.getRegDate();

    }
}
