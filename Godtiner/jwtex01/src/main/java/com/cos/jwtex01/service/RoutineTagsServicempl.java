/*
package com.cos.jwtex01.service;


import com.cos.jwtex01.dto.sharedRoutines.RoutineTagsDTO;
import com.cos.jwtex01.entity.RoutineTags;
import com.cos.jwtex01.entity.SharedRoutines;
import com.cos.jwtex01.repository.RoutineTagsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class RoutineTagsServicempl implements RoutineTagsService {

    private final RoutineTagsRepository routineTagsRepository;

    @Override
    public List<RoutineTagsDTO> getList(Long sharedId){
        List<RoutineTags> result = routineTagsRepository
                .getRoutineTagsBySno(SharedRoutines.builder().id(sharedId).build());

        return result.stream().map(tags -> entityToDTO(tags)).collect(Collectors.toList());
    }
}
*/
