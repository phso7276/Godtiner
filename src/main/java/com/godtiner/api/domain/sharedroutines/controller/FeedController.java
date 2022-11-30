package com.godtiner.api.domain.sharedroutines.controller;


import com.godtiner.api.domain.response.Response;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.FeedPageDto;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutineDetail;
import com.godtiner.api.domain.sharedroutines.service.SharedRoutinesService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Feed Controller", tags = "Feed Page")
@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "http://localhost:3000")
public class FeedController {

    private final SharedRoutinesService sharedRoutinesService;

    //피드 첫페이지 - 페이징 완료
    @GetMapping(value = "/feed/all")
    public Page<FeedPageDto> getFeedPage(@PageableDefault(size=6,sort="id",direction = Sort.Direction.DESC, page=0) final Pageable pageable) {
        return sharedRoutinesService.findAll(pageable).map(FeedPageDto::new);
    }

    //공유루틴 상세 페이지
    @GetMapping(value="/feed/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response detailRead(@PathVariable("id") Long id){
        log.info("-------------read-------------------");
        log.info(id);

        return Response.success(sharedRoutinesService.getDetail(id));

    }


}
