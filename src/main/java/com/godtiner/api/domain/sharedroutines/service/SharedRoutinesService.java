package com.godtiner.api.domain.sharedroutines.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.dto.MemberInfoDto;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.member.service.FileService;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateResponse;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesDto;
import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.domain.sharedroutines.*;
import com.godtiner.api.domain.sharedroutines.dto.TagDto;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.*;
import com.godtiner.api.domain.sharedroutines.repository.*;
import com.godtiner.api.global.exception.*;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class SharedRoutinesService {

    private final SharedRoutinesRepository sharedRoutinesRepository;
    private final SharedContentsRepository sharedContentsRepository;
    private final MyContentsRepository myContentsRepository;
    private final MyRoutinesRepository myRoutinesRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;


    private final TagRepository tagRepository;
    private final RoutineTagRepository routineTagRepository;

    private final LikedRepository likedRepository;

    public SharedRoutinesCreateResponse create(SharedRoutinesCreate req, MultipartFile image,
                                               Long[] tagIdList) {

        SharedRoutines sharedRoutines = req.toEntity(req, memberRepository);

        if (!image.isEmpty()) {
            if (!image.getContentType().startsWith("image")) {
                throw new FileUploadFailureException(new Exception());
            }
           // List<String> fileNames =fileService.save(image);
            String imageFileName =fileService.save(image);
            sharedRoutines.updateStoredFilename(imageFileName);
            sharedRoutines.updateFeedThumbnailFilename("s_"+imageFileName);
            sharedRoutines.updateDetailThumbnailFilename("d_"+imageFileName);
            sharedRoutines.updateOriginalFilenmae(image.getOriginalFilename());
        }
        sharedRoutinesRepository.save(sharedRoutines);

        for (Long tagId : tagIdList) {
            Tag tag = tagRepository.findById(tagId).orElseThrow();

            RoutineTag routineTag = new RoutineTag(tag, sharedRoutines);
            routineTagRepository.save(routineTag);
        }
        //Tag tag = tagRepository.findById(req2.get)
        //Optional<Tag> tag = tagRepository.findByTagName(req2.getTagName().stream().collect(i->i));


        /*req.getImage().ifPresent(
                file ->  sharedRoutines.updateStoredFilename(fileService.save(file))

        );*/
        return new SharedRoutinesCreateResponse(sharedRoutines.getId());
    }


/*
    @Transactional(readOnly = true)
    public Page<SharedRoutines> findAll(Pageable pageable) {
        return sharedRoutinesRepository.findAll(pageable);
    }
*/

    //상세페이지
    public SharedRoutineDetail getDetail(Long id) {

        SharedRoutines result = sharedRoutinesRepository.findByIdWithMember(id)
                .orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND));;
        if (result !=null) {
            result.addHits();
            return SharedRoutineDetail.toDto(result);
        }
        return null;
    }

    //찜하기
    public void addLiked(long boardId) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        SharedRoutines target = Optional.of(sharedRoutinesRepository.findById(boardId).get()).orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND));

        likedRepository.findByMemberAndSharedRoutine(member, target).ifPresent(none -> {
            throw new RuntimeException();
        });

        likedRepository.save(
                Liked.builder()
                        .sharedRoutines(target)
                        .member(member)
                        .build()
        );
        //좋아요 수 업데이트 추가
        target.addLikedCnt();
    }

    public void deleteLiked(long likedId, long boardId) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        SharedRoutines target = Optional.of(sharedRoutinesRepository.findById(boardId).get()).orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND));

        likedRepository.findByMemberAndSharedRoutine(member, target).orElseThrow(() -> new RuntimeException());

        likedRepository.deleteById(likedId);

        target.deleteLikedCnt();
    }

    public void delete(Long id) throws MyRoutinesException {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        SharedRoutines sharedRoutines = Optional.of(sharedRoutinesRepository.findById(id).get())
                .orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND));

        sharedRoutinesRepository.delete(sharedRoutines);

    }

    //공유 루틴 스크랩
    public void pick(Long[] contentsId,Long routineId) {
        //멤버로 내 루틴 아이디 찾아서 주입
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        MyRoutines myRoutines=myRoutinesRepository.findByWriter(member)
                .orElseThrow(() -> new MyRoutinesException(MyRoutinesExceptionType.MY_ROUTINES_NOT_FOUND));
        //배열 반복문
        for (Long contentId : contentsId) {//해당하는 아이디의 필드를 repository에서 찾아 주업
            SharedContents sharedContents = sharedContentsRepository.findById(contentId)
                    .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND));
            //일단 content만 복사..
            MyContents myContents =new MyContents(sharedContents.getContent(),myRoutines);
            myContentsRepository.save(myContents);

        }
        SharedRoutines sharedRoutines = sharedRoutinesRepository.findById(routineId)
                .orElseThrow(() -> new MyRoutinesException(MyRoutinesExceptionType.MY_ROUTINES_NOT_FOUND));
        sharedRoutines.addPickCnt();

    }


    //페이징, 검색
   public SharedPagingDto getPostList(Pageable pageable, SearchCondition searchCondition) {

       return new SharedPagingDto(sharedRoutinesRepository.search(searchCondition, pageable));
   }

}