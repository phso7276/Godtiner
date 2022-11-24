package com.godtiner.api.domain.member.dto;


import com.godtiner.api.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberInfoDto {
    private String name;
    private String nickname;
    private String email;

    public MemberInfoDto(String name, String nickname, String email){
        this.name = name;
        this.nickname=nickname;
        this.email=email;
    }


    @Builder
    public MemberInfoDto(Member user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();

    }
}
