package com.godtiner.api.domain.myroutines.dto.myRoutines;


import com.godtiner.api.domain.myroutines.dto.myContents.MyContentsDto;
import com.godtiner.api.domain.myroutines.MyRoutines;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
