package com.godtiner.api.domain.member;

import com.godtiner.api.domain.BaseEntity;
import com.godtiner.api.domain.myroutines.MyRoutines;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class Member extends BaseEntity implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private Long id; //primary Key

        @Column(nullable = false, length = 30, unique = true)
        private String email;//아이디

        private String password;//비밀번호

        @Column(nullable = true, length = 30)
        private String name;//이름(실명)

        @Column( length = 30)
        private String nickname;//별명

        @Enumerated(EnumType.STRING)
        private Role role;//권한 -> USER, ADMIN

        @Column(length = 1000)
        private String refreshToken;//RefreshToken

        @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
        private List<ProfileImage> profileImage; // 3




        @Column(name="stored_filename", nullable=true)
        private String stored_filename;

        @Column(name="filesize", nullable=true)
        private long filesize;

        @Column(length = 2000)
        private String thumbnailUrl;

        @Column(name = "ORIGINAL_FILE_NAME")
        private String originalFileName;

        @JsonIgnore
        @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<MyRoutines> myRoutinesList = new ArrayList<>();


        //연관관계 메서드
        public void addMyRoutines(MyRoutines myRoutines){
                //post의 writer 설정은 post에서 함
                myRoutinesList.add(myRoutines);
        }



        //== 정보 수정 ==//
        public void updatePassword(PasswordEncoder passwordEncoder, String password){
            this.password = passwordEncoder.encode(password);
        }

        public void updateName(String name){
            this.name = name;
        }

        public void updateNickname(String nickName){
            this.nickname = nickName;
        }


        public void updateRefreshToken(String refreshToken){
                this.refreshToken = refreshToken;
        }
        public void destroyRefreshToken(){
                this.refreshToken = null;
        }

        //== 패스워드 암호화 ==//
        public void encodePassword(PasswordEncoder passwordEncoder){
            this.password = passwordEncoder.encode(password);
        }

        //비밀번호 변경, 회원 탈퇴 시, 비밀번호를 확인하며, 이때 비밀번호의 일치여부를 판단하는 메서드입니다.
        public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
                return passwordEncoder.matches(checkPassword, getPassword());
        }


        //회원가입시, USER의 권한을 부여하는 메서드입니다.
        public void addUserAuthority() {
                this.role = Role.USER;
        }

        private void addImages(List<ProfileImage> added) { // 5
                added.stream().forEach(i -> {
                        profileImage.add(i);
                        i.initPost(this);
                });
        }

}
