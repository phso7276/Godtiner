package com.godtiner.api.domain.myroutines.dto.myRoutines;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyRoutinesInfo {
    private Long id;
    private String title;
    //private MemberInfoDto member;
    private List<MyContentsDto> myContentsList;

 /*   @Builder
    public MyRoutinesInfo(Member user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();

    }*/
}
