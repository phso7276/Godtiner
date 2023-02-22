package com.godtiner.api.domain.sharedroutines.controller;

import com.godtiner.api.domain.response.Response;
import com.godtiner.api.domain.sharedroutines.dto.LikedDelete;
import com.godtiner.api.domain.sharedroutines.dto.PickRequestDto;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutinesCreate;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutinesDelete;
import com.godtiner.api.domain.sharedroutines.service.SharedRoutinesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "Shared Routines Controller", tags = "SharedRoutines")
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class SharedRoutinesController {

    private final SharedRoutinesService sharedRoutinesService;

    @ApiOperation(value = "공유 루틴 생성", notes = "공유 루틴을 생성한다.")
    @PostMapping(value = "/sharedRoutine/post",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(/*@Valid */@RequestPart(value = "contents") SharedRoutinesCreate req,
                           @RequestPart(value = "file") MultipartFile file) throws Exception {
        return Response.success(sharedRoutinesService.create(req,file/*,tagIdList*/));
    }

    //공유 루틴 삭제

    @ApiOperation(value = "공유 루틴 삭제", notes = "공유 루틴을 삭제한다.")
    @PostMapping(value = "/sharedRoutine/remove")
    public Response deleteRoutines(@RequestBody SharedRoutinesDelete req) throws Exception {
        sharedRoutinesService.deleteRoutines(req);
        return Response.success();
    }


    //찜하기

    @ApiOperation(value = "공유 루틴 찜하기", notes = "공유 루틴을 찜한다.")
    @PostMapping("/sharedRoutine/{id}/liked")
    public Response addGoodPoint(@PathVariable long id){
        /*if (httpSession.getAttribute("USER") == null) {
            throw new FreeBoardException(UserExceptionType.LOGIN_INFORMATION_NOT_FOUND);
        }*/
        sharedRoutinesService.addLiked(id);

        return Response.success();
    }

   @DeleteMapping("/sharedRoutine/{id}/liked")
    public Response deleteGoodPoint(@PathVariable long id/*, @PathVariable long likedId*/){

        sharedRoutinesService.deleteLiked(/*likedId, */id);

       return Response.success();
    }

    //공유 루틴 스크랩
    @PostMapping("/sharedRoutine/pick/{routineId}")
    public Response pickSharedContents(@RequestBody PickRequestDto req,
                                       @PathVariable Long routineId){

        sharedRoutinesService.pick(req,routineId);

        return Response.success();
    }

    //내가 공유한 루틴들만 보기
    @GetMapping("/member/sharedRoutine")
    public Response getMySharedRoutines(){

        return Response.success(sharedRoutinesService.mySharedRoutines());
    }


    //찜한 루틴들 보기
    @GetMapping("/member/liked")
    public Response getMyLiked(){

        return Response.success(sharedRoutinesService.myLiked());
    }
    //찜하기 복수삭제 (마이페이지)

    @PostMapping(value = "/member/liked/remove")
    public Response deleteMyLikedList(@RequestBody LikedDelete req) throws Exception {
        sharedRoutinesService.deletelikedList(req);
        return Response.success();
    }


}
