package com.godtiner.api.domain.sharedroutines.dto.sharedRoutines;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedRoutinesDelete {

    private Long[] routineIdList;
}
