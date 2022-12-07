package com.godtiner.api.domain.myroutines;


import lombok.Getter;

@Getter
public class MyContentsAlarmEvent {

    private final MyContents myContents;

    public MyContentsAlarmEvent(MyContents myContents){
        this.myContents=myContents;
    }
}
