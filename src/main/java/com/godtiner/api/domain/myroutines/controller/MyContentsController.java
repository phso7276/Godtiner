package com.godtiner.api.domain.myroutines.controller;


import com.godtiner.api.domain.myroutines.dto.MyContentsCreate;
import com.godtiner.api.domain.myroutines.dto.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.dto.MyRoutinesCreateResponse;
import com.godtiner.api.domain.myroutines.service.MyContentsService;
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

@Api(value = "My Contents Controller", tags = "MyContents")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MyContentsController {

    private final MyContentsService myContentsService;
    private final MyRoutinesSerivce myRoutinesSerivce;

    @ApiOperation(value = "마이 루틴 항목 생성", notes = "마이루틴 항목을 생성한다.")
    @PostMapping("/myRoutine/details")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @ModelAttribute MyContentsCreate req, MyRoutinesCreateRequest create) throws Exception {
        //MyRoutinesCreateResponse myRoutinesCreateResponse =myRoutinesSerivce.save(create);
        myContentsService.save(req);
        return Response.success();
    }

}
