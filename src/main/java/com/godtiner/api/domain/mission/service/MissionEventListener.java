package com.godtiner.api.domain.mission.service;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.mission.MemberMission;
import com.godtiner.api.domain.mission.Mission;
import com.godtiner.api.domain.mission.MissionCompleteEvent;
import com.godtiner.api.domain.mission.repository.MemberMissionRepository;
import com.godtiner.api.domain.notification.Notification;
import com.godtiner.api.domain.notification.NotificationType;
import com.godtiner.api.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Async
@Transactional
@Component
@RequiredArgsConstructor
public class MissionEventListener {
    private final NotificationRepository notificationRepository;
    private final MemberMissionRepository missionRepository;

    @EventListener // (1)
    @Async
    public void handleMissionCompleteEvent(MissionCompleteEvent missionCompleteEvent) { // (2)
        Mission mission =missionCompleteEvent.getMission();
        MemberMission memberMission = missionRepository.findMemberMissionByMission(mission).orElseThrow();

        // TODO 이메일 보내거나 DB에 Notification 정보 저장
        saveNotification(mission, memberMission.getMember());
    }

    private void saveNotification(Mission mission, Member member) {
        notificationRepository.save(Notification.from(mission.getMissionName(),member, LocalDateTime.now(),false, NotificationType.MISSION_CLEAR));
    }
}
