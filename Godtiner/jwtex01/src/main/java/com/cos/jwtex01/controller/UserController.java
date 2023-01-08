package com.cos.jwtex01.controller;

import com.cos.jwtex01.dto.user.*;
import com.cos.jwtex01.entity.User;
import com.cos.jwtex01.service.UserService;
import com.cos.jwtex01.service.UserServicempl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@Log4j2
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public void signUp(@Valid @RequestBody UserSignUpDto memberSignUpDto) throws Exception {
       userService.signUp(memberSignUpDto);
    }

    /**
     * 회원정보수정
     */
    @PutMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public void updateBasicInfo(@Valid @RequestBody UserUpdateDto userUpdateDto) throws Exception {
        userService.update(userUpdateDto);
    }

    /**
     * 비밀번호 수정
     */
    @PutMapping("/member/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {
        userService.updatePassword(updatePasswordDto.getCheckPassword(),updatePasswordDto.getToBePassword());
    }


    /**
     * 회원탈퇴
     */
    @DeleteMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public void withdraw(@Valid @RequestBody UserWithdrawDto memberWithdrawDto) throws Exception {
        userService.withdraw(memberWithdrawDto.getCheckPassword());
    }


    /**
     * 회원정보조회
     */
    @GetMapping("/member/{id}")
    public ResponseEntity getInfo(@Valid @PathVariable("id") Long id) throws Exception {
        UserInfoDto info = userService.getInfo(id);
        return new ResponseEntity(info, HttpStatus.OK);
    }

    /**
     * 내정보조회
     */
    @GetMapping("/member")
    public ResponseEntity getMyInfo(HttpServletResponse response) throws Exception {

        UserInfoDto info =userService.getMyInfo();
        return new ResponseEntity(info, HttpStatus.OK);
    }

   /* @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> searchUser(
            @PathVariable final Long id) {
        User user = userServicempl.searchUser(id);
        if (user != null) {
            return ResponseEntity.ok(
                    new UserResponseDto(userServicempl.searchUser(id))
            );
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }*/



}
