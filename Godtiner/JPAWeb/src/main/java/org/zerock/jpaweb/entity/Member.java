package org.zerock.jpaweb.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @Column(name="email", length=50, nullable=false)
    private String email;

    @Column(name="username", length=50, nullable=false)
    private String username;

    @Column(name="password", length=200, nullable=false)
    private String password;

    @Column(name="telno", length=20, nullable=true)
    private String telno;

    @Column(name="nickname", length=50, nullable=true)
    private String nickname;

    @Column(name="lastpwdate", nullable=true)
    private LocalDateTime lastpwdate;

    @Column(name="pwcheck", nullable=true)
    private int pwcheck;

    /*@Column(name="role", nullable=true)
    private String role;
*/
    @Column(name="org_filename", nullable=true)
    private String org_filename;

    @Column(name="stored_filename", nullable=true)
    private String stored_filename;

    @Column(name="filesize", nullable=true)
    private long filesize;

    @Column(name="fromSocial", nullable=false)
    private boolean fromSocial;

    //사용자 정보 수정
    public void memberInfoUpdate(String telno,String nickname,
                                 String org_filename,String stored_filename,Long filesize) {


        this.telno = telno;
        this.nickname = nickname;
        this.org_filename = org_filename;
        this.stored_filename = stored_filename;
        this.filesize = filesize;

    }
/*
    //구글 회원 사용자 정보 수정
    public void googleMemberInfoUpdate(MemberDTO member) {

        this.username = member.getUsername();
        this.password = member.getPassword();
        this.pwcheck = member.getPwcheck();
        this.fromSocial = member.getFromSocial();
        this.regdate = member.getRegdate();
        this.lastpwdate = member.getLastpwdate();
        this.role = member.getRole();

        this.telno = member.getTelno();
        this.nickname = member.getNickname();
        this.org_filename = member.getOrg_filename();
        this.stored_filename = member.getStored_filename();
        this.filesize = member.getFilesize();

    }

    //사용자 패스워드 변경
    public void memberPasswordModify(String password) {

        this.password = password;
        this.lastpwdate = LocalDateTime.now();
        this.pwcheck = 1;
    }*/

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);

    }
}
