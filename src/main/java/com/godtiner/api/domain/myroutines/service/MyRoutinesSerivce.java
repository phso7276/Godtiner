package com.godtiner.api.domain.myroutines.service;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.myRoutines.*;
import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.global.exception.MyRoutinesException;
import com.godtiner.api.global.exception.MyRoutinesExceptionType;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MyRoutinesSerivce {

    private final MyRoutinesRepository myRoutinesRepository;
    private final MyContentsRepository myContentsRepository;
    private final MemberRepository memberRepository;

    private final MyContentsService myContentsService;


    /*public MyRoutinesCreateResponse save(MyRoutinesCreateRequest myRoutinesCreateRequest,MyContentsCreate myContentsCreate) throws MyContentsException {
        MyRoutines myRoutines = myRoutinesCreateRequest.toEntity();

        log.info("루틴 아이디:"+myRoutines.getId());

        myRoutines.confirmWriter(memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)));



       *//* myRoutinesCreateRequest.getMyContentsList().ifPresent(
                file -> myRoutines.updateFilePath(fileService.save(file))
        );*//*

        myRoutinesRepository.save(myRoutines);
        MyContents myContents = myContentsCreate.toEntity();
        log.info("루틴 아이디:"+myRoutines.getId());
       if(myRoutinesCreateRequest.getMyContentsList() !=null){
            log.info("콘텐츠 있음:"+ myRoutinesRepository.getWithWriter(myRoutines.getId()));
            myContents.confirmMyRoutines(myRoutines);
        }

        myRoutinesRepository.save(myRoutines);

        return new MyRoutinesCreateResponse(myRoutines.getId());
    }*/

    public MyRoutinesDto read(Long id){
       /* Member findMember = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
*/
        return new MyRoutinesDto(myRoutinesRepository.findById(id)
                .orElseThrow(() -> new MyRoutinesException(MyRoutinesExceptionType.MY_ROUTINES_NOT_FOUND)));
    }

/*
   public  MyRoutinesDto getInfoWithMember(Long mid){

        return new MyRoutinesDto(myRoutinesRepository.getWithWriter(mid)
                .orElseThrow(() -> new MyRoutinesException(MyRoutinesExceptionType.MY_ROUTINES_NOT_FOUND)));
    }
*/

    public MyRoutinesDto getInfoMine(){
        Member findMember = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        log.info("멤버 아이디:"+findMember.getId());

        return new MyRoutinesDto(myRoutinesRepository.getWithWriter(findMember.getId())
                .orElseThrow(() -> new MyRoutinesException(MyRoutinesExceptionType.MY_ROUTINES_NOT_FOUND)));
    }


    public MyRoutinesCreateResponse create(MyRoutinesCreateRequest req) {
        MyRoutines myRoutines = myRoutinesRepository.save(
                MyRoutinesCreateRequest.toEntity(
                        req,
                        memberRepository
                )
        );
        return new MyRoutinesCreateResponse(myRoutines.getId());
    }



    }
