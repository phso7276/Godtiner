package com.godtiner.api.domain.member.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MemberInterestRequest {

    private String email;
    private Long[] tagIdList;
}
