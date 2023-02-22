package com.godtiner.api.domain.myroutines.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.godtiner.api.domain.myroutines.MyContents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyContentsSimple {

    private long id;

    //루틴 순번
    private int idx;

    private String content;



    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm", timezone = "Asia/Seoul")
    private LocalTime startTime;
    //종료시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm", timezone = "Asia/Seoul")
    private LocalTime endTime;

    public MyContentsSimple(MyContents myContents) {
        this.id = myContents.getId();
        this.idx =myContents.getIdx();
        this.content =myContents.getContent();
        this.startTime =myContents.getStartTime();
        this.endTime = myContents.getEndTime();
    }


}
