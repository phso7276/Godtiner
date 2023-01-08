package com.cos.jwtex01.controller;


import com.cos.jwtex01.dto.myRoutines.MyCreateDto;
import com.cos.jwtex01.entity.MyRoutines;
import com.cos.jwtex01.entity.User;
import com.cos.jwtex01.repository.MyRoutinesRepository;
import com.cos.jwtex01.service.MyRoutinesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Log4j2
@RequestMapping
@RequiredArgsConstructor
public class MyRoutinesController {
    private final MyRoutinesService routinesService;

    private final MyRoutinesRepository myRoutinesRepository;

   /* @PostMapping("/myroutines/create")
    public ResponseEntity<MyResponseDto> createMyRoutines(@RequestBody final MyCreateDto myCreateDto){
        final MyRoutines myRoutines = routinesService.searchMyRoutines(myCreateDto.toEntity().getId());
        if(myRoutines ==null){

            return ResponseEntity.ok(
                    new MyResponseDto(routinesService.createMyRoutines(myCreateDto.toEntity()))
            );
        }
        return ResponseEntity.ok(
                new MyResponseDto(routinesService.searchMyRoutines(myCreateDto.toEntity().getId()))
        );

    }*/

    @PostMapping("/myroutines/create")
    public ResponseEntity<MyCreateDto> createMyRoutines(
            MyCreateDto myCreateDto) {
        //이 유저가 만든 독서모임이 있는지 체크(한사람당 한 개)
        //400 에러 -> 잘못된 요청
     /*   if(routinesService.findRoutinesByUserId(myCreateDto.getWriterid())!=null){

        }
*/
            MyRoutines myRoutines = routinesService.createMyRoutines(myCreateDto.toEntity());
            return new ResponseEntity("독서모임 등록이 완료되었습니다. (clubId: " + myRoutines.getId() + ")", HttpStatus.OK);

    }

    @PostMapping("/routine/join")
    public String join(@RequestBody User user) {
        Optional<MyRoutines> myRoutines = myRoutinesRepository.getWithWriter(user.getId());
        if(myRoutines!=null){
            return "이메일 중복";
        }
        else{
            /*myRoutinesRepository.save();*/
            return "회원가입완료";
        }

    }
}
