package org.zerock.jpaweb.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.jpaweb.entity.Member;
import org.zerock.jpaweb.repository.MemberRepository;
import org.zerock.jpaweb.security.dto.AuthMemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException{
        log.info("UserDetailService loadUSerByUsername "+username);

        Optional<Member> result = memberRepository.findByEmail(username,false);

        if(!result.isPresent()){
            throw new UsernameNotFoundException("check email or social");

        }

        Member member =result.get();

        log.info("---------------------------");
        log.info(member);

        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role->new SimpleGrantedAuthority
                                ("ROLE_"+role.name())).collect(Collectors.toSet())
    );
        authMember.setName(member.getUsername());
        authMember.setFromSocial(member.isFromSocial());
        return authMember;
    }
}
