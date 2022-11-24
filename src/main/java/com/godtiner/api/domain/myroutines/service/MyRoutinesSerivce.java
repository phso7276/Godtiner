package com.godtiner.api.domain.myroutines.service;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.ProfileImage;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.MyContentsCreate;
import com.godtiner.api.domain.myroutines.dto.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.dto.MyRoutinesCreateResponse;
import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.global.exception.MemberNotFoundException;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MyRoutinesSerivce {

    private final MyRoutinesRepository myRoutinesRepository;
    private final MyContentsRepository myContentsRepository;
    private final MemberRepository memberRepository;


    public MyRoutinesCreateResponse save(MyRoutinesCreateRequest myRoutinesCreateRequest) throws NullPointerException {
        MyRoutines myRoutines = myRoutinesCreateRequest.toEntity();

        myRoutines.confirmWriter(memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)));

       /* myRoutinesCreateRequest.getMyContentsList().ifPresent(
                file -> myRoutines.updateFilePath(fileService.save(file))
        );*/
     /*   if(myRoutinesCreateRequest.getMyContentsList().isPresent()){
            log.info("콘텐츠 있음");
        }
*/
        myRoutinesRepository.save(myRoutines);

        return new MyRoutinesCreateResponse(myRoutines.getId());
    }



  /*  @Transactional
    public MyRoutinesCreateResponse create(MyRoutinesCreateRequest req) {
        Member member = memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException::new);
        List<MyContents> myContentsList = req.getMyContentsList().stream().map(i -> new MyContents(i.getIdx(),i.getContent(),i.getStartTime(),i.getEndTime())).collect(toList());
        MyRoutines myRoutines = myRoutinesRepository.save(
               new MyRoutines(req.getTitle(),member,myContentsList)
        );

      *//*  uploadContents(myRoutines.getMyContentsList(), req.getMyContentsList());*//*
        return new MyRoutinesCreateResponse(myRoutines.getId());
    }
*/
 /*   private void uploadContents(List<MyContents> contents, List<MyContentsCreate> contentsList) {
        IntStream.range(0, contents.size()).forEach(i -> fileService.upload(fileImages.get(i), images.get(i).getUniqueName()));
    }*/
}
