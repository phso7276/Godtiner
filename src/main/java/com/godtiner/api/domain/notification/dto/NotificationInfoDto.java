package com.godtiner.api.domain.notification.dto;


import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.notification.Notification;
import com.godtiner.api.domain.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationInfoDto {


    private List<Notification> notifications =new ArrayList<>();

    private long numberOfChecked;

    public static NotificationInfoDto toDto(){
        return new NotificationInfoDto();
    }

    public NotificationInfoDto(NotificationRepository notificationRepository, Member member){
        this.notifications=notificationRepository.findByMemberAndCheckedOrderByCreatedDesc(member, false);
        this.numberOfChecked = notificationRepository.countByMemberAndChecked(member, true);
    }
}
