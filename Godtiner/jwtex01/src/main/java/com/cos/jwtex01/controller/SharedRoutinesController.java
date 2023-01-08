package com.cos.jwtex01.controller;

import com.cos.jwtex01.dto.sharedRoutines.FeedInfoDTO;
import com.cos.jwtex01.dto.sharedRoutines.SharedRoutinesDTO;
import com.cos.jwtex01.repository.SharedRepository;
import com.cos.jwtex01.service.SharedRoutinesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Log4j2
@RequestMapping
@RequiredArgsConstructor
public class SharedRoutinesController {
    private final SharedRepository sharedRepository;
    private final SharedRoutinesService sharedRoutinesService;

    @PostMapping(value ="/share")
    public ResponseEntity<Long> register(@RequestBody SharedRoutinesDTO sharedRoutinesDTO){
        log.info("--------------------register------------------");
        log.info(sharedRoutinesDTO);

        Long num = sharedRoutinesService.register(sharedRoutinesDTO);

        return new ResponseEntity<>(num, HttpStatus.OK);
    }
    //피드 첫페이지 - 페이징 완료
    @GetMapping(value = "/feed/all")
    public Page<FeedInfoDTO> getFeedPage(@PageableDefault(size=6,sort="id",direction = Sort.Direction.DESC, page=0) final Pageable pageable) {
        return sharedRoutinesService.findAll(pageable).map(FeedInfoDTO::new);
    }
 /*   @GetMapping(value = "/feed/all/details")
    public Page<SharedRoutinesDTO> getSharedRoutines(@PageableDefault(size=6,sort="id",direction = Sort.Direction.DESC, page=0) final Pageable pageable) {
        return sharedRoutinesService.findAll(pageable).map(SharedRoutinesDTO::new);
    }*/



    //전체 불러오기
   /* @GetMapping(value = "/feed")
    public ResponseEntity<List<SharedRoutines>> index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable) {

        Page<SharedRoutines> list = sharedRoutinesService.findAll(pageable);
       // model.addAttribute("contents", sharedRoutinesService.findAll(pageable));
        log.info("======================list=============");

        model.addAttribute("posts", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        log.info(model);

        return new ResponseEntity<>(list.getContent(),HttpStatus.OK);
    }
*/

    //공유루틴 상세 페이지
    @GetMapping(value="/feed/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SharedRoutinesDTO> detailRead(@PathVariable("id") Long id){
        log.info("-------------read-------------------");
        log.info(id);

        return new ResponseEntity<>(sharedRoutinesService.get(id),HttpStatus.OK);

    }

    @GetMapping(value="/sharedRoutines/user/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SharedRoutinesDTO>> getList(Long writerId){
        log.info("-------------get List-------------------");
        log.info(writerId);

        return new ResponseEntity<>(sharedRoutinesService.getAllWithWriterId(writerId),HttpStatus.OK);

    }

    @DeleteMapping(value="/feed/{id}",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("id") Long id){
        log.info("------------remove------------------");
        log.info(id);

        return new ResponseEntity<>("removed",HttpStatus.OK);

    }

    @PutMapping(value="/feed/{id}",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody SharedRoutinesDTO sharedRoutinesDTO){
        log.info("-------------modify-------------------");
        log.info(sharedRoutinesDTO);

        return new ResponseEntity<>("modified",HttpStatus.OK);

    }
}
