package com.godtiner.api.domain.member.service;



import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.MemberTag;
import com.godtiner.api.domain.member.dto.*;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.member.repository.MemberTagRepository;
import com.godtiner.api.domain.sharedroutines.Tag;
import com.godtiner.api.domain.sharedroutines.repository.TagRepository;
import com.godtiner.api.global.exception.FileUploadFailureException;
import com.godtiner.api.global.exception.MemberNotFoundException;
import com.godtiner.api.global.jwt.service.JwtService;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional
@Log4j2
public class MemberServicempl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public Member searchUser(Long id) {
        return memberRepository.findById(id).orElse(null);
    }
    private final FileService fileService;

    private final TagRepository tagRepository;

    private final MemberTagRepository memberTagRepository;

  /*  @Override
    public MemberSignInResponseDto signIn(MemberSignInDto req) throws Exception {

        loginService.loadUserByUsername(req.getEmail());
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(MemberNotFoundException::new);

        *//*String username = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();
        return new MemberSignInResponseDto(accessToken, refreshToken);*//*


    }
    private String extractUsername(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
*/
    @Override
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        Member member = memberSignUpDto.toEntity();
        member.addUserAuthority();
        member.encodePassword(passwordEncoder);

        if(memberRepository.findByEmail(memberSignUpDto.getEmail()).isPresent()){
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USERNAME);
        }

        memberRepository.save(member);

       /* memberSignUpDto.getTagList().stream().forEach(
                i->{
                    Tag tag = tagRepository.findById(i.getId()).orElseThrow();
                    MemberTag memberTag = new MemberTag(tag,member,i.getTagName());
                    memberTagRepository.save(memberTag);
                }
        );
*/
    }

    @Override
    public void update(MemberUpdateDto req/*,MultipartFile image*/) throws Exception {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

       /* req.getUploadImage().ifPresent(
                file -> member.updateFilePath(fileService.save(file))
        );*/
        /*if(member.getStored_filename() !=null){
            fileService.delete(member.getStored_filename());//기존에 올린 파일 지우기
            member.updateOriginalFilenmae(null);
        }


        if(!image.isEmpty()){
            if(!image.getContentType().startsWith("image")){
                throw new FileUploadFailureException(new Exception());
            }
            member.updateStoredFilename(fileService.profileSave(image));
            member.updateOriginalFilenmae(image.getOriginalFilename());
        }
*/

        //req.getName().ifPresent(member::updateName);\
        //member.updateNickname(String.valueOf(req.getNickname()));
        //member.updateIntroduction(String.valueOf(req.getIntroduction()));
        req.getNickname().ifPresent(member::updateNickname);
        req.getIntroduction().ifPresent(member::updateIntroduction);


    }



    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder, toBePassword);
    }

    @Override
    public void withdraw(String checkPassword) throws Exception {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        if(member.getStored_filename() !=null){
            fileService.delete(member.getStored_filename());//기존에 올린 파일 지우기
        }


        memberRepository.delete(member);

    }

    @Override
    public MemberInfoDto getInfo(Long id) throws Exception {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        return new MemberInfoDto(findMember);
    }

    @Override
    public MemberInfoDto getMyInfo() throws Exception {
        Member findMember = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        return new MemberInfoDto(findMember);
    }

    @Override
    public void saveInterest(MemberInterestRequest memberInterestRequest) throws Exception {

        Member member = memberRepository.findByEmail(memberInterestRequest.getEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        for(Long tagId:memberInterestRequest.getTagIdList()){
            Tag tag = tagRepository.findById(tagId).orElseThrow();
            MemberTag memberTag = new MemberTag(tag,member,tag.getTagName());
            memberTagRepository.save(memberTag);

        }
    }


}
