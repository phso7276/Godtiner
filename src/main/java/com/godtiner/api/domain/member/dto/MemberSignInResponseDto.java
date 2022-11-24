package com.godtiner.api.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberSignInResponseDto {
    private String accessToken;
    private String refreshToken;
}
