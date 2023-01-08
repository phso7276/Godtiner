/*
package com.cos.jwtex01.service;

import com.cos.jwtex01.dto.sharedRoutines.RoutineTagsDTO;
import com.cos.jwtex01.entity.RoutineTags;
import com.cos.jwtex01.entity.SharedRoutines;

import java.util.List;

public interface RoutineTagsService {

*/
/*    List<RoutineTagsDTO> getList(Long sharedId);*//*


    default RoutineTags dtoToEntity(RoutineTagsDTO routineTagsDTO){
        SharedRoutines sharedRoutines = SharedRoutines.builder().id(routineTagsDTO.getSharedId()).build();

        RoutineTags routineTags = RoutineTags.builder()
                .id(routineTagsDTO.getId())
                .idx(routineTagsDTO.getIdx())
                .tag(routineTagsDTO.getTag())
                .sharedRoutines(sharedRoutines)
                .build();

        return routineTags;
    }
    default RoutineTagsDTO entityToDTO(RoutineTags routineTags){
        RoutineTagsDTO dto = RoutineTagsDTO.builder()
                .id(routineTags.getId())
                .idx(routineTags.getIdx())
                .tag(routineTags.getTag())
                .build();

        return dto;
    }
}
*/
