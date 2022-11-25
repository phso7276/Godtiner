package com.godtiner.api.domain.myroutines.dto.myRoutines;


import com.godtiner.api.domain.member.dto.MemberInfoDto;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MyRoutinesDto {
    private Long id;
    private String title;
    //private MemberInfoDto member;
    private List<MyContentsDto> myContentsList;

   public MyRoutinesDto( MyRoutines myRoutines) {

       this.id= myRoutines.getId();
       this.title = myRoutines.getTitle();
       this.myContentsList = myRoutines.getMyContentsList().stream()
               .map(MyContentsDto::new).collect(Collectors.toList());

    }
}
