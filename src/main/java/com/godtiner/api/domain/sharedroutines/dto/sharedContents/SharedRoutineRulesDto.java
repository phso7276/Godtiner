package com.godtiner.api.domain.sharedroutines.dto.sharedContents;

import com.godtiner.api.domain.sharedroutines.SharedRoutineRules;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedRoutineRulesDto {
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;

    public SharedRoutineRulesDto(SharedRoutineRules sharedRoutineRules) {
        this.mon= sharedRoutineRules.isMon();
        this.tue= sharedRoutineRules.isTue();
        this.wed= sharedRoutineRules.isWed();
        this.thu = sharedRoutineRules.isThu();
        this.fri= sharedRoutineRules.isFri();
        this.sat= sharedRoutineRules.isSat();
        this.sun= sharedRoutineRules.isSun();
    }


}
