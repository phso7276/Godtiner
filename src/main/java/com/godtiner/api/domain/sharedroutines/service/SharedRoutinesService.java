package com.godtiner.api.domain.sharedroutines.service;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.MemberTag;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.member.repository.MemberTagRepository;
import com.godtiner.api.domain.member.service.FileService;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutines;

import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.domain.sharedroutines.*;
import com.godtiner.api.domain.sharedroutines.dto.PickRequestDto;
import com.godtiner.api.domain.sharedroutines.dto.RecommendationPageDto;
import com.godtiner.api.domain.sharedroutines.dto.TagInfo;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.*;
import com.godtiner.api.domain.sharedroutines.repository.*;
import com.godtiner.api.global.exception.*;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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

    private final MemberTagRepository memberTagRepository;

    private final LikedRepository likedRepository;



    /*public SharedRoutinesCreateResponse create(SharedRoutinesCreate req, MultipartFile image
                                               *//*Long[] tagIdList*//*) {

        SharedRoutines sharedRoutines = req.toEntity(req, memberRepository);

        for (Long contentId : req.getMyContentsIdList()) {//해당하는 아이디의 필드를 repository에서 찾아 주업
            MyContents myContents = myContentsRepository.findById(contentId)
                    .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND));
            //일단 content만 복사..
            SharedContents sharedContents =new SharedContents(myContents.getContent(),myContents.getStartTime(),
                    myContents.getEndTime(),sharedRoutines);
            sharedContentsRepository.save(sharedContents);

        }

        if (!image.isEmpty()) {
            String imageFileName =fileService.save(image);
            sharedRoutines.updateStoredFilename(imageFileName);
            sharedRoutines.updateFeedThumbnailFilename("s_"+imageFileName);
            sharedRoutines.updateDetailThumbnailFilename("d_"+imageFileName);
            sharedRoutines.updateOriginalFilenmae(image.getOriginalFilename());
        }
        sharedRoutinesRepository.save(sharedRoutines);

        for (Long tagId : req.getTagIdList()) {
            Tag tag = tagRepository.findById(tagId).orElseThrow();

            RoutineTag routineTag = new RoutineTag(tag, sharedRoutines,tag.getTagName());
            routineTagRepository.save(routineTag);

            routineTagConverter.convertToDatabaseColumn(routineTag);
        }

        return new SharedRoutinesCreateResponse(sharedRoutines.getId());
    }*/

    public SharedRoutinesCreateResponse create(SharedRoutinesCreate req, MultipartFile image) {

        Member member=  memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(MemberNotFoundException::new);

        /*SharedRoutines sharedRoutines = SharedRoutinesCreate.toEntity(req, memberRepository);*/
        SharedRoutines sharedRoutines = SharedRoutinesCreate.toEntity(req, member);
        for (Long contentId : req.getMyContentsIdList()) {//해당하는 아이디의 필드를 repository에서 찾아 주업
            MyContents myContents = myContentsRepository.findById(contentId)
                    .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND));
            //일단 content만 복사..
            SharedContents sharedContents =new SharedContents(myContents.getContent(),myContents.getStartTime(),
                    myContents.getEndTime(),sharedRoutines);
            sharedContentsRepository.save(sharedContents);

        }

        if (!image.isEmpty()) {
            String imageFileName =fileService.save(image);
            sharedRoutines.updateStoredFilename(imageFileName);
            sharedRoutines.updateFeedThumbnailFilename("s_"+imageFileName);
            sharedRoutines.updateDetailThumbnailFilename("d_"+imageFileName);
            sharedRoutines.updateOriginalFilenmae(image.getOriginalFilename());
        }
        sharedRoutinesRepository.save(sharedRoutines);

       /* req.getTagList().stream().forEach(
                i-> tagRepository.findById(i.getId()).ifPresent(
                       tag-> routineTagRepository.save(new RoutineTag(tag,sharedRoutines,tag.getTagName()))
                )
        );*/

        for (TagInfo tags : req.getTagList()) {
            Tag tag = tagRepository.findById(tags.getId()).orElseThrow();

            /*RoutineTag routineTag = new RoutineTag(tag, sharedRoutines,tag.getTagName());
            routineTagRepository.save(routineTag);*/

            routineTagRepository.save(new RoutineTag(tag, sharedRoutines,tag.getTagName()));

        }

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

    public RecommendationPageDto getRecommendation(){
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        //멤버의 관심사 받아오기

       List<MemberTag> memberTagList;
       memberTagList=  memberTagRepository.findByMember(member);

    /*   log.info("멤버태그:"+memberTagList.get(0).getTagName());


       for(MemberTag memberTag:memberTagList) {
           log.info(memberTag.getTagName());

       }*/


        //관심사로 루틴태그 찾기
       /* RoutineTag routineTag1 = routineTagRepository.findByTagName(memberTagList.get(0).getTagName())
                .orElseThrow();

        RoutineTag routineTag2 = routineTagRepository.findByTagName(memberTagList.get(1).getTagName())
                .orElseThrow();*/

        //관심사에 해당하는 모든 루틴 찾기
        //Iterator<SharedRoutines> sharedRoutines =sharedRoutinesRepository.findByRoutineTags()

        return new RecommendationPageDto(memberTagList,sharedRoutinesRepository.getSharedRoutinesByTagName(memberTagList.get(0).getTagName()),
                sharedRoutinesRepository.getSharedRoutinesByTagName(memberTagList.get(1).getTagName()));

    }

    //찜하기
    public void addLiked(long boardId) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        SharedRoutines target = Optional.of(sharedRoutinesRepository.findById(boardId).get())
                .orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND));

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
    public void pick(PickRequestDto req, Long routineId) {
        //멤버로 내 루틴 아이디 찾아서 주입
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        MyRoutines myRoutines=myRoutinesRepository.findByWriter(member)
                .orElse(myRoutinesRepository.save(new MyRoutines("내 루틴",member)) );
        //배열 반복문
        for (Long contentId : req.getContentIdList()) {//해당하는 아이디의 필드를 repository에서 찾아 주업
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

       return new SharedPagingDto(sharedRoutinesRepository.search(searchCondition, pageable),tagRepository);
   }



}