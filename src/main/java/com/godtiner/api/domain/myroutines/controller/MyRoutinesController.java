package com.godtiner.api.domain.myroutines.controller;


import com.godtiner.api.domain.myroutines.dto.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.service.MyRoutinesSerivce;
import com.godtiner.api.domain.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "My Routines Controller", tags = "MyRoutines")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MyRoutinesController {

    private final MyRoutinesSerivce myRoutinesSerivce;

    @ApiOperation(value = "마이루틴 생성", notes = "마이루틴을을 생성한다.")
    @PostMapping("/myRoutine/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @ModelAttribute MyRoutinesCreateRequest req) throws Exception {
        return Response.success(myRoutinesSerivce.save(req));
    }
}
