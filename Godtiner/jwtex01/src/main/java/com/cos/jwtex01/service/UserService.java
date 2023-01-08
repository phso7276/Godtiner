package com.cos.jwtex01.service;

import com.cos.jwtex01.dto.user.UserInfoDto;
import com.cos.jwtex01.dto.user.UserSignUpDto;
import com.cos.jwtex01.dto.user.UserUpdateDto;

public interface UserService {

    void signUp(UserSignUpDto UserSignUpDto) throws Exception;

    void update(UserUpdateDto UserUpdateDto) throws Exception;

    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    void withdraw(String checkPassword) throws Exception;

    UserInfoDto getInfo(Long id) throws Exception;

    UserInfoDto getMyInfo() throws Exception;
}
