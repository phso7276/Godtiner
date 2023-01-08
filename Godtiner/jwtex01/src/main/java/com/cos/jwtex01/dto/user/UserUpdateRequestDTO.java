package com.cos.jwtex01.dto.user;

import com.cos.jwtex01.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDTO implements Serializable {
    private Long id;
    private String userName;
    private String email;
    private String stored_filename;


    @Builder
    public UserUpdateRequestDTO(User user){
        this.id = user.getId();
        this.userName = user.getName();
        this.stored_filename = user.getStored_filename();
        this.email=getEmail();
    }

}