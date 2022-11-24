package com.godtiner.api.domain.member.dto;

import com.godtiner.api.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSignUpDto {

    private String email;
    private String password;
    private String name;
    private String nickname;




public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .build();
        }
    public MemberSignUpDto(String email,String password, String name, String nickname){
        this.email = email;
        this.password =password;
        this.name = name;
        this.nickname = nickname;
    }

}
