package com.godtiner.api.domain.member.dto;


import com.godtiner.api.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSignInDto {

    private String email;
    private String password;



    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
    public MemberSignInDto(String email,String password, String name, String nickname){
        this.email = email;
        this.password =password;

    }

}
