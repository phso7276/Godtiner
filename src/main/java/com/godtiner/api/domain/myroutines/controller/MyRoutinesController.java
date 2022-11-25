package com.godtiner.api.domain.myroutines.controller;


import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsCreate;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesDto;
import com.godtiner.api.domain.myroutines.service.MyRoutinesSerivce;
import com.godtiner.api.domain.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "My Routines Controller", tags = "MyRoutines")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MyRoutinesController {

    private final MyRoutinesSerivce myRoutinesSerivce;

    @ApiOperation(value = "마이루틴 생성", notes = "마이루틴을 생성한다.")
    @PostMapping("/myRoutine/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody MyRoutinesCreateRequest req) throws Exception {
        return Response.success(myRoutinesSerivce.create(req));
    }

    @ApiOperation(value = "마이루틴 루틴 아이디로 조회 ", notes = "마이루틴을 조회한다.")
    @GetMapping("/myRoutine/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@ApiParam(value = "루틴 id", required = true) @PathVariable Long id) {
        return Response.success(myRoutinesSerivce.read(id));
    }

/*
   @ApiOperation(value = "마이루틴 멤버 아이디로 조회 ", notes = "마이루틴을 조회한다.")
    @GetMapping(value = "/myRoutine",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response get(Long mid) {
        return Response.success(myRoutinesSerivce.getInfoWithMember(mid));
    }
*/

    @ApiOperation(value = "마이루틴 멤버 아이디로 조회 ", notes = "마이루틴을 조회한다.")
    @GetMapping(value = "/myRoutine/post")
    @ResponseStatus(HttpStatus.OK)
    public Response getMine() {
        return Response.success(myRoutinesSerivce.getInfoMine());
    }
}
