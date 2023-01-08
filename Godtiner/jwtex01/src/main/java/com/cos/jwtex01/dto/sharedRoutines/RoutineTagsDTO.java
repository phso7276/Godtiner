package com.cos.jwtex01.dto.sharedRoutines;

import com.cos.jwtex01.entity.RoutineTags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineTagsDTO {

    private Long id;
    private String tag;
    private int idx;
    private Long sharedId; //공유 루틴 번호

   public RoutineTagsDTO(RoutineTags routineTags) {
        this.id =routineTags.getId();
        this.idx = routineTags.getIdx();
        this.tag = routineTags.getTag();
        this.sharedId =routineTags.getSharedRoutines().getId();

    }
}
