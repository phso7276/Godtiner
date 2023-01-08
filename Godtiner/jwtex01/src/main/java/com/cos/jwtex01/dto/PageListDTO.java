package com.cos.jwtex01.dto;


import com.cos.jwtex01.dto.sharedRoutines.SharedRoutinesDTO;
import com.cos.jwtex01.entity.SharedRoutines;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PageListDTO {

    private List<SharedRoutinesDTO> sharedRoutinesDTOList = new ArrayList<>();
    private long totalPages;
    private long totalCount;

/*
    @Builder
    public PageListDTO(List<SharedRoutines> sharedList, long totalPages,long totalCount){
        this.sharedRoutinesDTOList =sharedList.stream().map(SharedRoutinesDTO::new)
                .collect(Collectors.toList());
        this.totalPages =totalPages;
        this.totalCount = totalCount;
    }*/
}
