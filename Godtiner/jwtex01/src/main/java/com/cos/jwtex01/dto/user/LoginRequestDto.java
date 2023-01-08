package com.cos.jwtex01.dto.user;

import lombok.Data;

@Data
public class LoginRequestDto {
	private String email;
	private String password;
}
