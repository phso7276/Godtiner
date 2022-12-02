package com.godtiner.api.domain.myroutines.service;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutineRules;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsCreate;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsUpdateRequest;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsUpdateResponse;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.domain.myroutines.repository.MyRoutineRulesRepository;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.global.exception.MyContentsException;
import com.godtiner.api.global.exception.MyContentsExceptionType;
import com.godtiner.api.global.exception.MyRoutinesException;
import com.godtiner.api.global.exception.MyRoutinesExceptionType;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MyContentsService {
    private final MyContentsRepository myContentsRepository;
    private final MemberRepository memberRepository;
    private final MyRoutinesRepository myRoutinesRepository;

    private final MyRoutineRulesRepository myRoutineRulesRepository;

    public void save(MyContentsCreate req){
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        Optional<MyRoutines> myRoutines = myRoutinesRepository.findByWriter(member);

        if(myRoutines.isEmpty()){
            MyRoutines routine = new MyRoutines("내 루틴",member);
            myRoutinesRepository.save(routine);

            myContentsRepository.save( MyContentsCreate.toEntity(
                    req,routine.getId(),
                    myRoutinesRepository
            ));
        }
        else{

            myContentsRepository.save( MyContentsCreate.toEntity(
                    req,myRoutines.get().getId(),
                    myRoutinesRepository
            ));
        }





        /*myContentsRepository.save( MyContentsCreate.toEntity(
                req,id,
               myRoutinesRepository
        ));*/
    }

    public MyContentsUpdateResponse update(Long id, MyContentsUpdateRequest req) {

        MyContents myContents = myContentsRepository.findById(id)
                .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND));

        MyRoutineRules myRoutineRules = myRoutineRulesRepository.findByMyContentsId(myContents)
                .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND));
      /*  if(!myContents.getMyRoutines().getId().equals(SecurityUtil.getLoginUsername())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_UPDATE_COMMENT);
        }*/

       req.getContent().ifPresent(myContents::updateContent);
       req.getStartTime().ifPresent(myContents::updateStartTime);
       req.getEndTime().ifPresent(myContents::updateEndTime);
       req.getMon().ifPresent(myRoutineRules::updateMon);
       req.getTue().ifPresent(myRoutineRules::updateTue);
       req.getWed().ifPresent(myRoutineRules::updateWed);
       req.getThu().ifPresent(myRoutineRules::updateThu);
       req.getFri().ifPresent(myRoutineRules::updateFri);
        req.getSun().ifPresent(myRoutineRules::updateSun);
        req.getSat().ifPresent(myRoutineRules::updateSat);

       return new MyContentsUpdateResponse(id);
    }

    public void delete(Long id){
        MyContents myContents = myContentsRepository.findById(id)
                .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND));

        //checkAuthority(myContents,MyContentsExceptionType.NOT_AUTHORITY_DELETE_MY_CONTENTS);

        myContentsRepository.delete(myContents);
    }

   /* private void checkAuthority(MyContents myContents , MyContentsExceptionType myContentsExceptionType) {
        if(!myContents.getMyRoutines().getId().equals(SecurityUtil.getLoginEmail()))
            throw new MyContentsException(myContentsExceptionType);
    }*/
   public void clear(Long id){
        MyContents myContents = myContentsRepository.findById(id)
                .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND));

        if(!myContents.isClear()){
            myContents.clear();
        }
        else {
            myContents.cancelClear();
        }

    }
}
