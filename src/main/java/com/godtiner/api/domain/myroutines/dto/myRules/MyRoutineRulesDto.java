package com.godtiner.api.domain.myroutines.dto.myRules;


import com.fasterxml.jackson.annotation.JsonFormat;
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

   // private Long contentsId;
    private Long id;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regdate;

    public MyRoutineRulesDto(MyRoutineRules myRoutineRules) {
        //this.contentsId = myRoutineRules.getMyContentsId().getId();
        this.id= myRoutineRules.getId();
        this.mon=myRoutineRules.isMon();
        this.tue=myRoutineRules.isTue();
        this.wed=myRoutineRules.isWed();
        this.thu=myRoutineRules.isThu();
        this.fri=myRoutineRules.isFri();
        this.sat=myRoutineRules.isSat();
        this.sun=myRoutineRules.isSun();
        this.regdate=myRoutineRules.getRegdate();

    }
}
