package com.cos.jwtex01.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {

    private Optional<String> name;
    private Optional<String> nickname;
}
