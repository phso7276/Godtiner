package com.godtiner.api.domain.notification.controller;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.notification.Notification;
import com.godtiner.api.domain.notification.dto.NotificationInfoDto;
import com.godtiner.api.domain.notification.repository.NotificationRepository;
import com.godtiner.api.domain.notification.service.NotificationService;
import com.godtiner.api.domain.response.Response;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final MemberRepository memberRepository;

    @GetMapping("/notifications")
    @ResponseStatus(HttpStatus.OK)
    public Response getNotifications() {
        /*Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));*/
      NotificationInfoDto notifications= notificationService.getNotification();
       //List<Notification> notifications = notificationRepository.findByMemberAndCheckedOrderByCreatedDesc(member, false);
        //long numberOfChecked = notificationRepository.countByMemberAndChecked(member, true);
        /*putCategorizedNotifications(model, notifications.getNotifications(), notifications.getNumberOfChecked(), notifications.getNotifications().size());
        model.addAttribute("isNew", true);*/
       notificationService.markAsRead(notifications.getNotifications());
        return Response.success(notifications);
    }

    @GetMapping("/notifications/old")
    @ResponseStatus(HttpStatus.OK)
    public Response getOldNotifications(Model model) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        List<Notification> notifications = notificationRepository.findByMemberAndCheckedOrderByCreatedDesc(member, true);
        long numberOfNotChecked = notificationRepository.countByMemberAndChecked(member, false);
        putCategorizedNotifications(model, notifications, notifications.size(), numberOfNotChecked);
        model.addAttribute("isNew", false);
        return Response.success();
    }

    @DeleteMapping("/notifications")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteNotifications() {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        notificationRepository.deleteByMemberAndChecked(member, true);
        return Response.success();
    }

    private void putCategorizedNotifications(Model model, List<Notification> notifications, long numberOfChecked, long numberOfNotChecked) {
        ArrayList<Notification> newMissionCompleteNotifications = new ArrayList<>();
        ArrayList<Notification> eventEnrollmentNotifications = new ArrayList<>();
        ArrayList<Notification> watchingStudyNotifications = new ArrayList<>();
        for (Notification notification : notifications) {
            switch (notification.getNotificationType()) {
                case MISSION_CLEAR: {
                    newMissionCompleteNotifications.add(notification);
                    break;
                }
                /*case EVENT_ENROLLMENT: {
                    eventEnrollmentNotifications.add(notification);
                    break;
                }
                case STUDY_UPDATED: {
                    watchingStudyNotifications.add(notification);
                    break;
                }*/
            }
        }
        model.addAttribute("numberOfNotChecked", numberOfNotChecked);
        model.addAttribute("numberOfChecked", numberOfChecked);
        model.addAttribute("notifications", notifications);
        model.addAttribute("newMissionCompleteNotifications", newMissionCompleteNotifications);
        model.addAttribute("eventEnrollmentNotifications", eventEnrollmentNotifications);
        model.addAttribute("watchingStudyNotifications", watchingStudyNotifications);
    }


}