package com.cos.jwtex01.dto.user;

import com.cos.jwtex01.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoDto {
    private final String name;
    private final String nickname;
    private final String email;



    @Builder
    public UserInfoDto(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();

    }
}
