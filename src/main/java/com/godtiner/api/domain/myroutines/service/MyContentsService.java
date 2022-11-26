package com.godtiner.api.domain.myroutines.service;

import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsCreate;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsUpdateRequest;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsUpdateResponse;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.global.exception.MyContentsException;
import com.godtiner.api.global.exception.MyContentsExceptionType;
import com.godtiner.api.global.util.security.SecurityUtil;
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

    public void save(Long id, MyContentsCreate req){
       /* MyContents myContents = req.toEntity();
        myContents.confirmMyRoutines(myRoutinesRepository.findById(id)
                .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND)));
*/
        //myContentsRepository.save(myContents);

        myContentsRepository.save( MyContentsCreate.toEntity(
                req,id,
               myRoutinesRepository
        ));
    }

    public MyContentsUpdateResponse update(Long id, MyContentsUpdateRequest req) {

        MyContents myContents = myContentsRepository.findById(id)
                .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND));
      /*  if(!myContents.getMyRoutines().getId().equals(SecurityUtil.getLoginUsername())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_UPDATE_COMMENT);
        }*/

       req.getContent().ifPresent(myContents::updateContent);
       req.getStartTime().ifPresent(myContents::updateStartTime);
       req.getEndTime().ifPresent(myContents::updateEndTime);

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
}
