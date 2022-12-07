package com.godtiner.api;


import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.Role;
import com.godtiner.api.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class UserTests {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){
        //1-49 user
        IntStream.rangeClosed(1,16).forEach(i->{
            Member user = Member.builder()
                    .email("u"+i+"@rmail.com")
                    .name("사용자"+i)
                    .password(passwordEncoder.encode("11111"))
                    .role(Role.valueOf("USER"))
                    .build();

            //default Role
          /* user.setRoles("USER");
            if(i>17){
                member.addMemberRole(MemberRole.MANAGER);
            }
            if(i>19){
                member.addMemberRole(MemberRole.ADMIN);
            }*/

            repository.save(user);
        });
    }

/*    @Test
    public void testRead(){
        Optional<User> result = repository.findByEmail("u10@rmail.com",false);

        User user =result.get();

        System.out.println(user);
    }*/
}
