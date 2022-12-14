package com.godtiner.api.domain.sharedroutines.controller;

import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateRequest;
import com.godtiner.api.domain.response.Response;
import com.godtiner.api.domain.sharedroutines.dto.PickRequestDto;
import com.godtiner.api.domain.sharedroutines.dto.TagDto;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.FeedPageDto;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutinesCreate;
import com.godtiner.api.domain.sharedroutines.service.SharedRoutinesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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
    @DeleteMapping(value = "/sharedRoutine/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response delete(@PathVariable long id) throws Exception {
        sharedRoutinesService.delete(id);
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

   @DeleteMapping("/sharedRoutine/{boardId}/liked/{likedId}")
    public Response deleteGoodPoint(@PathVariable long boardId, @PathVariable long likedId){

        sharedRoutinesService.deleteLiked(likedId, boardId);

       return Response.success();
    }

    //공유 루틴 스크랩
    @PostMapping("/sharedRoutine/pick/{routineId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response pickSharedContents(@RequestBody PickRequestDto req,
                                       @PathVariable Long routineId){

        sharedRoutinesService.pick(req,routineId);

        return Response.success();
    }




}
