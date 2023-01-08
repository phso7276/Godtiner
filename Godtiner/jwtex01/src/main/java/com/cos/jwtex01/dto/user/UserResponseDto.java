package com.cos.jwtex01.dto.user;

import com.cos.jwtex01.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto { //DTO : 로직x. 순수한 데이터 객체, getter, setter 만을 가짐

    private Long id;
    private String name;
    private String email;
    private String imgUrl;

    public UserResponseDto(User user) {


        BeanUtils.copyProperties(user, this);
    }
}