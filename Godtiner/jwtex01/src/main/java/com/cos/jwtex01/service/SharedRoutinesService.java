package com.cos.jwtex01.service;

import com.cos.jwtex01.dto.sharedRoutines.SharedContentsDTO;
import com.cos.jwtex01.dto.sharedRoutines.SharedRoutinesDTO;
import com.cos.jwtex01.entity.SharedRoutines;
import com.cos.jwtex01.entity.User;
import com.cos.jwtex01.repository.SharedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public interface SharedRoutinesService {

    @Autowired
    SharedRepository sharedRepository = null;
    Long register(SharedRoutinesDTO sharedRoutinesDTO);

    //PageResultDTO<SharedRoutinesDTO, SharedRoutines> getList(PageRequestDTO requestDTO);
    SharedRoutinesDTO get(Long num);

    void modify(SharedRoutinesDTO sharedRoutinesDTO);

    void remove(Long id);

    //전체 공유루틴 조회
    Page<SharedRoutines> findAll(Pageable pageable);



    //public List<SharedRoutines> findSharedRoutines();
    //Page<SharedRoutines> sharedRoutinesList(String searchType, String keyword, int pageNum, int postNum);
    List<SharedRoutinesDTO> getAllWithWriterId(Long writerId);

    default SharedRoutines dtoToEntity(SharedRoutinesDTO sharedRoutinesDTO){
        SharedRoutines sharedRoutines = SharedRoutines.builder()
                .id(sharedRoutinesDTO.getId())
                .title(sharedRoutinesDTO.getTitle())
                .content(sharedRoutinesDTO.getContent())
                .stored_filename(sharedRoutinesDTO.getStored_filename())
                .originalFileName(sharedRoutinesDTO.getOriginal_filename())
                .filesize(sharedRoutinesDTO.getFilesize())
                .thumbnailUrl(sharedRoutinesDTO.getThumbnailUrl())
                .writer(User.builder().id(sharedRoutinesDTO.getWriterId()).build())
                .build();

        return sharedRoutines;
    }

    default SharedRoutinesDTO entityToDTO(SharedRoutines sharedRoutines){
        SharedRoutinesDTO sharedRoutinesDTO = SharedRoutinesDTO.builder()
                .id(sharedRoutines.getId())
                .title(sharedRoutines.getTitle())
                .tagList(sharedRoutines.getTagList().stream().map(SharedRoutinesDTO.RoutineTagsDTO::new).collect(Collectors.toList()))
                .content(sharedRoutines.getContent())
                .regDate(sharedRoutines.getRegDate())
                .modDate(sharedRoutines.getModDate())
                .stored_filename(sharedRoutines.getStored_filename())
                .original_filename(sharedRoutines.getOriginalFileName())
                .filesize(sharedRoutines.getFilesize())
                .thumbnailUrl(sharedRoutines.getThumbnailUrl())
                .writerId(sharedRoutines.getWriter().getId())
                .writerNickname(sharedRoutines.getWriter().getNickname())
                .sharedContentsList(sharedRoutines.getSharedContentsList().stream()
                        .map(SharedContentsDTO::new).collect(Collectors.toList()))
                .build();

        return sharedRoutinesDTO;
    }

}
