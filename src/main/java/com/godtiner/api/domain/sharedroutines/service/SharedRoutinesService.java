package com.godtiner.api.domain.sharedroutines.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.objectweb.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
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

    private final ObjectMapper objectMapper = new ObjectMapper();


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

        Member isMemberLike = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));


        SharedRoutines sharedRoutines = sharedRoutinesRepository.findByIdWithMember(id)
                .orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND));
        if (sharedRoutines !=null) {
            //JSONObject data = new JSONObject();
            JSONObject result =byPass("http://127.0.0.1:5000/cb/"+id,"GET");

            //Long[] jsonList = (Long[]) result.get("id");
            //log.info("RESPONSE DATA : " + result.size());
            //log.info("RESPONSE DATA : " + result.get("data"));
            String jsonString = (String) result.get("data");

            List<SharedRoutines> routines= new ArrayList<>();

            try {
                IdList idList= objectMapper.readValue(jsonString, IdList.class);

                for(Long ids: idList.getId()){
                    //log.info("idList:"+ids);
                    routines.add(sharedRoutinesRepository.findById(ids)
                            .orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND)));
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e){
                return SharedRoutineDetail.toDto(sharedRoutines,routines, false);
            }
            sharedRoutines.addHits();
            Optional<Liked> liked =  likedRepository.findByMemberAndSharedRoutine(isMemberLike,sharedRoutines);
            if (liked.isPresent()){
                return SharedRoutineDetail.toDto(sharedRoutines,routines, true);
            }
            else {
                return SharedRoutineDetail.toDto(sharedRoutines,routines, false);
            }

        }
        return null;
    }

    public RecommendationPageDto getRecommendation(){
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        //멤버의 관심사 받아오기

       List<MemberTag> memberTagList;
       memberTagList=  memberTagRepository.findByMember(member);
       Long isLikedPresent =  likedRepository.countByMember(member);

        if (isLikedPresent>0) {
            //JSONObject data = new JSONObject();
            JSONObject result =byPass("http://127.0.0.1:5000/recommend/"+member.getId(),"GET");

            String jsonString = (String) result.get("data");

            List<SharedRoutines> routines= new ArrayList<>();

            try {
                IdList idList= objectMapper.readValue(jsonString, IdList.class);

                for(Long ids: idList.getId()){
                    log.info("idList:"+ids);
                    routines.add(sharedRoutinesRepository.findById(ids)
                            .orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND)));
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e){
                return new RecommendationPageDto(memberTagList,sharedRoutinesRepository.getSharedRoutinesByTagName(memberTagList.get(0).getTagName()),
                        sharedRoutinesRepository.getSharedRoutinesByTagName(memberTagList.get(1).getTagName()));
            }
            return new RecommendationPageDto(memberTagList,sharedRoutinesRepository.getSharedRoutinesByTagName(memberTagList.get(0).getTagName()),
                    sharedRoutinesRepository.getSharedRoutinesByTagName(memberTagList.get(1).getTagName()),routines);
        }


        return new RecommendationPageDto(memberTagList,sharedRoutinesRepository.getSharedRoutinesByTagName(memberTagList.get(0).getTagName()),
                sharedRoutinesRepository.getSharedRoutinesByTagName(memberTagList.get(1).getTagName()));

    }

    //찜하기
    public void addLiked(long boardId) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        SharedRoutines target = sharedRoutinesRepository.findById(boardId)
                .orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND));

        likedRepository.findByMemberAndSharedRoutine(member, target).ifPresent(none -> {
            throw new RuntimeException();
        });
        likedRepository.save(
                new Liked(target,member)
        );


       /* likedRepository.save(
                Liked.builder()
                        .sharedRoutines(target)
                        .member(member)
                        .build()
        );*/
        //좋아요 수 업데이트 추가
        target.addLikedCnt();
    }

    public void deleteLiked(/*long likedId,*/ long boardId) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        SharedRoutines target = Optional.of(sharedRoutinesRepository.findById(boardId).get())
                .orElseThrow(() -> new SharedRoutinesException(SharedRoutinesExceptionType.SHARED_ROUTINES_NOT_FOUND));

        Liked liked = likedRepository.findByMemberAndSharedRoutine(member, target).orElseThrow(RuntimeException::new);

        likedRepository.deleteById(liked.getId());

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
                .orElseGet(()-> myRoutinesRepository.save(new MyRoutines("내 루틴",member)) );
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

    public JSONObject byPass(String url,/* JSONObject jsonData,*/ String option) {
        log.info("***************** BY PASS START*****************");
        JSONObject responseJson = new JSONObject();
        try {
            // 연결할 url 생성
            URL start_object = new URL(url);
            log.info("CONNECT URL :" + url);

            // http 객체 생성
            HttpURLConnection start_con = (HttpURLConnection) start_object.openConnection();
            start_con.setDoOutput(true);
            start_con.setDoInput(true);

            // 설정 정보
            start_con.setRequestProperty("Content-Type", "application/json");
            start_con.setRequestProperty("Accept", "application/json");
            log.info("Option:"+option);
            start_con.setRequestMethod(option);

            // data 전달
            //log.info("REQUEST DATA : " + jsonData);

            // 출력 부분
           /* OutputStreamWriter wr = new OutputStreamWriter(start_con.getOutputStream());
            wr.write(jsonData.toString());
            wr.flush();*/

            // 응답 받는 부분
            //StringBuilder start_sb = new StringBuilder();
            String sb = "";
            int start_HttpResult = start_con.getResponseCode();

            // 결과 성공일 경우 = HttpResult 200일 경우
            if (start_HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(start_con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    //start_sb.append(line);
                    sb = sb + line + "\n";
                }
                responseJson.put("data", sb.toString());
               responseJson.put("result", "SUCCESS");
                br.close();
                log.info("***************** BY PASS SUCCESS *****************");
                return responseJson;
            } else {
                // 그 외의 경우(실패)
                log.info("***************** BY PASS FAIL *****************");
                responseJson.put("result", "FAIL");
                return responseJson;
            }
        } catch (Exception e) {
            log.info("***************** BY PASS FAIL Exception *****************");
            log.info(e.toString());
            responseJson.put("result", "EXCEPTION");
            return responseJson;
        }
    }


}
