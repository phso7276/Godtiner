package com.godtiner.api.domain.myroutines.dto.myRoutines;


import com.godtiner.api.domain.member.dto.MemberInfoDto;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsDto;
import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class MyRoutinesDto {
    private Long id;
    private String title;
    //private MemberInfoDto member;
    private List<MyContentsDto> myContentsList;

   public MyRoutinesDto(MyRoutines myRoutines, List<MyContentsDto> myContents) {

       this.id= myRoutines.getId();
       this.title = myRoutines.getTitle();
       /*this.myContentsList = myRoutines.getMyContentsList().stream()
               .map(MyContentsDto::new).collect(Collectors.toList());*/
       this.myContentsList =myContents;

    }


}
