package com.godtiner.api.domain.member.service;


import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.dto.MemberInfoDto;
import com.godtiner.api.domain.member.dto.MemberSignUpDto;
import com.godtiner.api.domain.member.dto.MemberUpdateDto;

public interface MemberService {

    void signUp(MemberSignUpDto UserSignUpDto) throws Exception;

    void update(MemberUpdateDto UserUpdateDto) throws Exception;

    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    void withdraw(String checkPassword) throws Exception;

    MemberInfoDto getInfo(Long id) throws Exception;

    MemberInfoDto getMyInfo() throws Exception;
}
