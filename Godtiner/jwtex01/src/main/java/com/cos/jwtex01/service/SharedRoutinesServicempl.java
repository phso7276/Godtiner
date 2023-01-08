package com.cos.jwtex01.service;


import com.cos.jwtex01.dto.sharedRoutines.SharedRoutinesDTO;
import com.cos.jwtex01.entity.SharedRoutines;
import com.cos.jwtex01.repository.SharedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class SharedRoutinesServicempl implements SharedRoutinesService{
    private final SharedRepository sharedRepository;


    @Override
    public Long register(SharedRoutinesDTO sharedRoutinesDTO) {
        SharedRoutines sharedRoutines = dtoToEntity(sharedRoutinesDTO);

        log.info("-===========================");
        log.info(sharedRoutines);

        sharedRepository.save(sharedRoutines);

        return sharedRoutines.getId();
    }

   /* @Override
    public PageResultDTO<SharedRoutinesDTO, SharedRoutines> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        Page<SharedRoutines> result = sharedRepository.findAll(pageable);

        Function<SharedRoutines, SharedRoutinesDTO> fn = (entity-> entityToDTO(entity));

        return new PageResultDTO<>(result,fn);
    }*/



    //t상세페이지
    @Override
    public SharedRoutinesDTO get(Long id) {

        Optional<SharedRoutines> result = sharedRepository.getWithWriter(id);
        if(result.isPresent()){
            return entityToDTO(result.get());
        }
        return null;
    }

    @Override
    public void modify(SharedRoutinesDTO sharedRoutinesDTO) {
        Long id = sharedRoutinesDTO.getId();
        Optional<SharedRoutines> result = sharedRepository.findById(id);

        if(result.isPresent()){
            SharedRoutines sharedRoutines = result.get();

            //추가 구현
            sharedRoutines.changeTitle(sharedRoutinesDTO.getTitle());
            sharedRoutines.changeContent(sharedRoutinesDTO.getContent());
            sharedRepository.save(sharedRoutines);
        }
    }

    @Override
    public void remove(Long id) {
        sharedRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SharedRoutines> findAll(Pageable pageable) {
        return sharedRepository.findAll(pageable);
    }





   @Override
    public List<SharedRoutinesDTO> getAllWithWriterId(Long writerId) {
       List<SharedRoutines> sharedRoutinesList = sharedRepository.getList(writerId);

       return sharedRoutinesList.stream().map(sharedRoutines -> entityToDTO(sharedRoutines))
               .collect(Collectors.toList());

   }


}
