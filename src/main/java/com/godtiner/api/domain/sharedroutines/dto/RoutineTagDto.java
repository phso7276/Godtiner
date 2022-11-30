package com.godtiner.api.domain.sharedroutines.dto;


import com.godtiner.api.domain.sharedroutines.RoutineTag;
import com.godtiner.api.domain.sharedroutines.Tag;
import com.godtiner.api.domain.sharedroutines.dto.sharedContents.SharedRoutineRulesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineTagDto {
    private Long id;


    private TagInfo tag;

    public RoutineTagDto(RoutineTag routineTag) {
        this.id=routineTag.getId();
        this.tag= TagInfo.toDto(routineTag.getTag());
    }

    public RoutineTagDto toDto(RoutineTag routineTag) {
        return new RoutineTagDto(
                routineTag.getId(),
                TagInfo.toDto(routineTag.getTag())

        );
    }
}
