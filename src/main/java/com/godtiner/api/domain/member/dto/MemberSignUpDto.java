package com.godtiner.api.domain.member.dto;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.sharedroutines.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberSignUpDto {

    private String email;
    private String password;
    private String name;
    private String nickname;

    //private List<Tag> tagList;




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
