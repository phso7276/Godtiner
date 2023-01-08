package org.zerock.jpaweb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


@Log4j2
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final PasswordEncoder pwdEncoder;


    //로그인 화면 보기
    @RequestMapping(value="/member/login",method=RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void getLogIn(Model model, @RequestParam(name="message", required=false) String message)
            throws Exception{

        model.addAttribute("message", message);

    }

    //로그인 화면 보기
    @RequestMapping(value="/member/login",method=RequestMethod.POST)
    public void postLogIn(Model model,@RequestParam(name="message", required=false) String message)
            throws Exception{

        model.addAttribute("message", message);

    }
    //사용자 등록 화면 보기
    @RequestMapping(value="/member/signup",method=RequestMethod.GET)
    public void getMemberRegistry() throws Exception { }

}
