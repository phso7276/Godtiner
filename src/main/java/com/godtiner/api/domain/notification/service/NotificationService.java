package com.godtiner.api.domain.notification.service;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.notification.Notification;
import com.godtiner.api.domain.notification.dto.NotificationInfoDto;
import com.godtiner.api.domain.notification.repository.NotificationRepository;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutineDetail;
import com.godtiner.api.global.exception.SharedRoutinesException;
import com.godtiner.api.global.exception.SharedRoutinesExceptionType;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    public final NotificationRepository notificationRepository;
    public final MemberRepository memberRepository;
    public void markAsRead(List<Notification> notifications) {

        notifications.forEach(Notification::read);
    }

    public NotificationInfoDto getNotification() {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        return new NotificationInfoDto(notificationRepository,member);
    }



    /*public List<Notification> getNotificationList(){
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        log.info("멤버 아이디:"+member.getId());

        // NotificationInfoDto notifications= notificationService.getNotification();

        return notificationRepository.findByMemberAndCheckedOrderByCreatedDesc(member, false);
    }*/
}
