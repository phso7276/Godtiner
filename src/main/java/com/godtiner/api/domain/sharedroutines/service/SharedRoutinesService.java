package com.godtiner.api.domain.sharedroutines.service;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.member.service.FileService;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateResponse;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesDto;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.domain.sharedroutines.RoutineTag;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.Tag;
import com.godtiner.api.domain.sharedroutines.dto.TagDto;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutinesCreate;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutinesCreateResponse;
import com.godtiner.api.domain.sharedroutines.repository.RoutineTagRepository;
import com.godtiner.api.domain.sharedroutines.repository.SharedRoutinesRepository;
import com.godtiner.api.domain.sharedroutines.repository.TagRepository;
import com.godtiner.api.global.exception.FileUploadFailureException;
import com.godtiner.api.global.exception.MyRoutinesException;
import com.godtiner.api.global.exception.MyRoutinesExceptionType;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class SharedRoutinesService {

    private final SharedRoutinesRepository sharedRoutinesRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    private final MyRoutinesRepository myRoutinesRepository;
    private final TagRepository tagRepository;
    private final RoutineTagRepository routineTagRepository;

    public SharedRoutinesCreateResponse create(SharedRoutinesCreate req, MultipartFile image,
                                               TagDto req2) {

        SharedRoutines sharedRoutines = req.toEntity(req,memberRepository);
        Tag tag = req2.toEntity(req2);
        tagRepository.save(tag);



        if(!image.isEmpty()){
            if(!image.getContentType().startsWith("image")){
                throw new FileUploadFailureException(new Exception());
            }
            sharedRoutines.updateStoredFilename(fileService.save(image));
            sharedRoutines.updateOriginalFilenmae(image.getOriginalFilename());
        }
        sharedRoutinesRepository.save(sharedRoutines);
        RoutineTag routineTag = new RoutineTag(tag,sharedRoutines);
        routineTagRepository.save(routineTag);

        /*req.getImage().ifPresent(
                file ->  sharedRoutines.updateStoredFilename(fileService.save(file))

        );*/
        return new SharedRoutinesCreateResponse(sharedRoutines.getId());
    }


    @Transactional(readOnly = true)
    public Page<SharedRoutines> findAll(Pageable pageable) {
        return sharedRoutinesRepository.findAll(pageable);
    }




}
