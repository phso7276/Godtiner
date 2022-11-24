package com.godtiner.api.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberWithdrawDto {

    private String checkPassword;

    public MemberWithdrawDto(String checkPassword){
        this.checkPassword=checkPassword;
    }
}
