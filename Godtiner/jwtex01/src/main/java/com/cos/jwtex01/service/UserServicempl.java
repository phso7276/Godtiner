package com.cos.jwtex01.service;


import com.cos.jwtex01.dto.user.UserInfoDto;
import com.cos.jwtex01.dto.user.UserSignUpDto;
import com.cos.jwtex01.dto.user.UserUpdateDto;
import com.cos.jwtex01.dto.user.UserUpdateRequestDTO;
import com.cos.jwtex01.entity.User;
import com.cos.jwtex01.repository.UserRepository;
import com.cos.jwtex01.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServicempl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User searchUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public Long updateUser(UserUpdateRequestDTO requestDto) throws IOException {



        // check request data is exist
        if(requestDto.getStored_filename()==null && requestDto.getUserName()==null){
            throw new IllegalArgumentException("변경할 정보가 존재하지 않습니다.");
        }

        // user entity
        User user = userRepository.findByEmail(requestDto.getEmail());


        // change user name
        if(user.isValidUpdateName(requestDto.getUserName())){
            user.updateName(requestDto.getUserName());
        }

        /**
         * 설명 : 변경한 이미지 저장
         * */
        // change user image
        if(requestDto.getStored_filename()!=null){
            if(!user.getStored_filename().isEmpty()) {
                String path = "c:\\Repository\\file\\";
                File file = new File(path + user.getStored_filename());
                file.delete();
            }
        }


        // update user info
        userRepository.save(user);

        // return user id
        return user.getId();
    }

  /*  public void remove(Long id){
        userRepository.deleteById(id);
    }*/

    @Override
    public void signUp(UserSignUpDto UserSignUpDto) throws Exception {
        User user = UserSignUpDto.toEntity();
        user.addUserAuthority();
        user.encodePassword(passwordEncoder);

        if(userRepository.findByEmail(UserSignUpDto.getEmail()) != null){
            throw new Exception("이미 존재하는 아이디입니다.");
        }

        userRepository.save(user);
    }

    @Override
    public void update(UserUpdateDto userUpdateDto) throws Exception {
        User user = userRepository.findByEmail(SecurityUtil.getLoginEmail());
        if(user == null){
            throw new Exception("회원이 존재하지 않습니다");
        }
        userUpdateDto.getName().ifPresent(user::updateName);
        userUpdateDto.getNickname().ifPresent(user::updateNickname);

    }

    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        User user = userRepository.findByEmail(SecurityUtil.getLoginEmail());
        if(user == null){
            throw new Exception("회원이 존재하지 않습니다");
        }

        if(!user.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(passwordEncoder, toBePassword);
    }

    @Override
    public void withdraw(String checkPassword) throws Exception {
        User user = userRepository.findByEmail(SecurityUtil.getLoginEmail());
        if(user == null){
            throw new Exception("회원이 존재하지 않습니다");
        }
        if(!user.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);

    }

    @Override
    public UserInfoDto getInfo(Long id) throws Exception {
        User findUser = userRepository.findById(id).orElseThrow(() -> new Exception("회원이 없습니다"));
        return new UserInfoDto(findUser);
    }

    @Override
    public UserInfoDto getMyInfo() throws Exception {
        User findUser = userRepository.findByEmail(SecurityUtil.getLoginEmail());
        if(findUser == null){
            throw new Exception("회원이 존재하지 않습니다");
        }

        return new UserInfoDto(findUser);
    }
}
