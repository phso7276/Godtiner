package com.godtiner.api.domain.sharedroutines.controller;

import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateRequest;
import com.godtiner.api.domain.response.Response;
import com.godtiner.api.domain.sharedroutines.dto.TagDto;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutinesCreate;
import com.godtiner.api.domain.sharedroutines.service.SharedRoutinesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api(value = "Shared Routines Controller", tags = "SharedRoutines")
@RestController
@RequiredArgsConstructor
@Slf4j
public class SharedRoutinesController {

    private final SharedRoutinesService sharedRoutinesService;

    @ApiOperation(value = "공유 루틴 생성", notes = "공유 루틴을 생성한다.")
    @PostMapping(value = "/sharedRoutine/post",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestPart(value = "contents") SharedRoutinesCreate req,
                           @RequestPart(value = "tags") TagDto req2,
                           @RequestPart(value = "file") MultipartFile file) throws Exception {
        return Response.success(sharedRoutinesService.create(req,file,req2));
    }

}
