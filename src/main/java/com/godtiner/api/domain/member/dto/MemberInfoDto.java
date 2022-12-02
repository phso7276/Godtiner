package com.godtiner.api.domain.member.dto;


import com.godtiner.api.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {
    private Long id;
    private String name;
    private String nickname;
    private String email;

    private String stored_filename;

/*    public MemberInfoDto(String name, String nickname, String email, String stored_filename){
        this.name = name;
        this.nickname=nickname;
        this.email=email;
        this.stored_filename= stored_filename;
    }*/


    @Builder
    public MemberInfoDto(Member user) {
        this.id=user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.stored_filename = user.getStored_filename();

    }

    public MemberInfoDto(Long id, String email, String name, String nickname) {
    }

    public static MemberInfoDto toDto(Member member) {
        return new MemberInfoDto(member.getId(), member.getEmail(), member.getName(), member.getNickname(),member.getStored_filename());
    }
}
