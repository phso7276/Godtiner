package com.godtiner.api.domain.myroutines;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Async
@Transactional(readOnly = true)
@Component
public class MyAlarmListener {

    @EventListener // (1)
    public void handleAlarmEvent(MyContentsAlarmEvent myContentsAlarmEvent) { // (2)
        MyContents myContents =myContentsAlarmEvent.getMyContents();
        log.info("<"+myContents.getContent(),"> 루틴 시작 시간 :"+myContents.getStartTime());
        // DB에 Notification 정보 저장
    }
}
