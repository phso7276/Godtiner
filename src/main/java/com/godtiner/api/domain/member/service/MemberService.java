package com.godtiner.api.domain.member.service;


import com.godtiner.api.domain.member.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    //MemberSignInResponseDto signIn(MemberSignInDto memberSignInDto) throws Exception;
    void signUp(MemberSignUpDto UserSignUpDto) throws Exception;

    void update(MemberUpdateDto req/*, MultipartFile image*/) throws Exception;

    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    void withdraw(String checkPassword) throws Exception;

    MemberInfoDto getInfo(Long id) throws Exception;

    MemberInfoDto getMyInfo() throws Exception;

    void saveInterest(MemberInterestRequest memberInterestRequest) throws Exception;


}
