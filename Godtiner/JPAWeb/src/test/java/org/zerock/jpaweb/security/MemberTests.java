package org.zerock.jpaweb.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.jpaweb.entity.Member;
import org.zerock.jpaweb.entity.MemberRole;
import org.zerock.jpaweb.repository.MemberRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){
        //1-49 user
        IntStream.rangeClosed(1,20).forEach(i->{
            Member member = Member.builder()
                    .email("u"+i+"@rmail.com")
                    .username("사용자"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("11111"))
                    .build();

            //default Role
            member.addMemberRole(MemberRole.USER);
            if(i>17){
                member.addMemberRole(MemberRole.MANAGER);
            }
            if(i>19){
                member.addMemberRole(MemberRole.ADMIN);
            }

            repository.save(member);
        });
    }

    @Test
    public void testRead(){
        Optional<Member> result = repository.findByEmail("u10@rmail.com",false);

        Member member =result.get();

        System.out.println(member);
    }
}
