package com.godtiner.api.domain.myroutines.controller;


import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsCreate;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsUpdateRequest;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.service.MyContentsService;
import com.godtiner.api.domain.myroutines.service.MyRoutinesSerivce;
import com.godtiner.api.domain.response.Response;
import com.godtiner.api.global.exception.MyContentsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "My Contents Controller", tags = "MyContents")
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class MyContentsController {

    private final MyContentsService myContentsService;
    private final MyRoutinesSerivce myRoutinesSerivce;

   /* @ApiOperation(value = "마이 루틴 항목 생성", notes = "마이루틴 항목을 생성한다.")
    @PostMapping("/myRoutine/detail/{rid}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@ApiParam(value = "루틴 id", required = true) @PathVariable Long rid,
                           @Valid @RequestBody MyContentsCreate req) throws MyContentsException {

       myContentsService.save(rid,req);
        return Response.success();
    }*/

    @ApiOperation(value = "마이 루틴 항목 생성", notes = "마이루틴 항목을 생성한다.")
    @PostMapping("/myRoutine/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Response routineCreate(@Valid @RequestBody MyContentsCreate req) throws MyContentsException {

        return Response.success(myContentsService.save(req));
    }

    @ApiOperation(value = "마이 루틴 항목 수정", notes = "마이루틴 항목을 수정한다.")
    @PutMapping("/myRoutine/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response update(@ApiParam(value = "항목 id", required = true) @PathVariable Long id,
                           @Valid @RequestBody MyContentsUpdateRequest req) throws MyContentsException {

        return Response.success(myContentsService.update(id,req));
    }

    @ApiOperation(value = "마이 루틴 항목 삭제", notes = "마이루틴 항목을 삭제한다.")
    @DeleteMapping("/myRoutine/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response remove(@ApiParam(value = "항목 id", required = true) @PathVariable Long id) throws MyContentsException {
        myContentsService.delete(id);
        return Response.success();
    }

    @ApiOperation(value = "루틴 클리어 변경 ", notes = "클리어 상태 변경 한다.")
    @PutMapping(value = "/myRoutine/clear/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateClear(@PathVariable Long id) {
        myContentsService.clear(id);
        return Response.success();
    }

}
