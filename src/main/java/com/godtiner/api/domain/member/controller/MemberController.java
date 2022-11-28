package com.godtiner.api.domain.member.controller;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.dto.*;
import com.godtiner.api.domain.member.service.LoginService;
import com.godtiner.api.domain.member.service.LoginSuccessJWTProvideHandler;
import com.godtiner.api.domain.member.service.MemberService;
import com.godtiner.api.domain.response.Response;
import com.godtiner.api.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.godtiner.api.domain.response.Response.success;

@RestController
@Log4j2
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

    private final MemberService memberService;
    private final LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler;

    /**
     * 회원가입
     */
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public Response signUp(@Valid @RequestBody MemberSignUpDto memberSignUpDto) throws Exception {
       memberService.signUp(memberSignUpDto);

        return success();
    }

/*   @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    public Response signIn(@Valid @RequestBody MemberSignInDto memberSignInDto
                                      ) throws Exception {

        return success();
    }*/

    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    public Response signIn() throws Exception {
        log.info("%%%%%%%%%%%%%%%%%로그인%%%%%%%%%%%%%%%%");
        return success();
    }
    /**
     * 회원정보수정
     */
    @PutMapping(value = "/member",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateBasicInfo(@Valid MemberUpdateDto userUpdateDto,@RequestParam(value="image") MultipartFile image) throws Exception {
        memberService.update(userUpdateDto,image);
    }

    /**
     * 비밀번호 수정
     */
    @PutMapping("/member/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {
       memberService.updatePassword(updatePasswordDto.getCheckPassword(),updatePasswordDto.getToBePassword());
    }


    /**
     * 회원탈퇴
     */
    @DeleteMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public void withdraw(@Valid @RequestBody MemberWithdrawDto memberWithdrawDto) throws Exception {
        memberService.withdraw(memberWithdrawDto.getCheckPassword());
    }


    /**
     * 회원정보조회
     */
    @GetMapping("/member/{id}")
    public ResponseEntity getInfo(@Valid @PathVariable("id") Long id) throws Exception {
        MemberInfoDto info = memberService.getInfo(id);
        return new ResponseEntity(info, HttpStatus.OK);
    }

    /**
     * 내정보조회
     */
    @GetMapping("/member")
    public ResponseEntity getMyInfo(HttpServletResponse response) throws Exception {

        MemberInfoDto info =memberService.getMyInfo();
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
