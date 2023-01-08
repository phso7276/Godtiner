package com.cos.jwtex01.dto.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordDto {

    private String checkPassword;
    private String toBePassword;
}
