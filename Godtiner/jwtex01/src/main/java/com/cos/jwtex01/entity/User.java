package com.cos.jwtex01.entity;

import javax.management.relation.Role;
import javax.persistence.*;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="name", length=50, nullable=true)
    private String name;
    @Column(name="password", length=200, nullable=false)
    private String password;
    @Column(unique = true, name="email", length=50, nullable=false)
    private String email;
    @Column(name="role", nullable=true)
    private String roles;
    @Column(name="provider_id", nullable=true)
    private String providerId;
    @Column(name="provider", nullable=true)
    private String provider;
    @Column(name="nickname", length=50, nullable=true)
    private String nickname;


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
    private List<SharedRoutines> sharedRoutinesList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyRoutines> myRoutinesList = new ArrayList<>();

    /*@JsonIgnore
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<MyRoutines> myRoutines = new ArrayList<>();*/

    // ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
    public boolean isValidUpdateName(String name){

        // null name
        if(name==null){
            return false;
        }

        // check same name
        if(this.name.equals(name)){
            return false;
        }

        return true;
    }




    // 이름 업데이트
    public void updateName(String name){
        this.name = name;
    }
    public void updateNickname(String nickName){
        this.nickname = nickName;
    }



    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }
    //비밀번호 변경, 회원 탈퇴 시, 비밀번호를 확인하며, 이때 비밀번호의 일치여부를 판단하는 메서드입니다.
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }


    //연관관계 메서드
    public void addMyRoutines(MyRoutines myRoutines){
        //post의 writer 설정은 post에서 함
        myRoutinesList.add(myRoutines);
    }

    public void addSharedRoutines(SharedRoutines sharedRoutines){
        //post의 writer 설정은 post에서 함
        sharedRoutinesList.add(sharedRoutines);
    }
    //== 패스워드 암호화 ==//
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    //회원가입시, USER의 권한을 부여하는 메서드입니다.
    public void addUserAuthority() {
        this.roles = "Role_USER";
    }





}
