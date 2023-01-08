package org.zerock.jpaweb.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zerock.jpaweb.security.dto.AuthMemberDTO;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void exAll(){
        log.info("exAll..");

    }
    @PreAuthorize("#authMember !=null &&#authMember.username eq\"u10@rmail.com\"")
    @RequestMapping(value = "/member",method = RequestMethod.GET)
    public void exMember(@AuthenticationPrincipal AuthMemberDTO authMember){
        log.info("exMember..");

        log.info("-------------------");
        log.info(authMember);
    }

    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void exAdmin(){
        log.info("exAdmin..");
    }
}
