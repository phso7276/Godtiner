package org.zerock.jpaweb.security.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.jpaweb.repository.MemberRepository;
import org.zerock.jpaweb.entity.Member;
import org.zerock.jpaweb.entity.MemberRole;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder pwdEncoder;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("-------------------------------------");
        log.info("userRequest: "+ userRequest);

        String clientName = userRequest.getClientRegistration().
                getClientName();

        log.info("clientName : "+ clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("=============================");
        oAuth2User.getAttributes().forEach((k,v)->{
            log.info(k+":"+v); //sub, picture, email, email_verified, EMAIL 등이 출력
        });

        String email =null;

        if(clientName.equals("Google")){
            //구글을 이용하는 경우
            email= oAuth2User.getAttribute("email");


        }
        log.info("EMAIL:"+email);

        Member member =saveSocialMember(email);

        return oAuth2User;
    }
    private Member saveSocialMember(String email){
        //기존에 동일한 이메일로 가입한 회원이 있다면 조회만
        Optional<Member> result = memberRepository.findByEmail(email,true);

        if(result.isPresent()){
            return result.get();
        }

        //없다면 패스워드는 임의로 11111 이름은 그냥 이메일 주소로
        Member member =Member.builder().email(email)
                .username(email)
                .password(pwdEncoder.encode("11111"))
                .fromSocial(true)
                .build();

        member.addMemberRole(MemberRole.USER);

        memberRepository.save(member);

        return member;
    }
}
