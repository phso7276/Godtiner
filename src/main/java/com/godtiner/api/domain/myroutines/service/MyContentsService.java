package com.godtiner.api.domain.myroutines.service;

import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.dto.MyContentsCreate;
import com.godtiner.api.domain.myroutines.dto.MyContentsCreateResponse;
import com.godtiner.api.domain.myroutines.dto.MyRoutinesCreateResponse;
import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.global.exception.MyRoutinesException;
import com.godtiner.api.global.exception.MyRoutinesExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MyContentsService {
    private final MyContentsRepository myContentsRepository;
    private final MemberRepository memberRepository;
    private final MyRoutinesRepository myRoutinesRepository;

    public void save(MyContentsCreate req){
      /*  MyContents myContents = myContentsCreate.toEntity();

        myContents.confirmMyRoutines(myRoutinesRepository.findById(rid).orElseThrow(()->new MyRoutinesException(MyRoutinesExceptionType.MY_ROUTINES_NOT_FOUND)));

        myContentsRepository.save(myContents);*/

        myContentsRepository.save(req.toEntity(req,myRoutinesRepository));

    }
}
