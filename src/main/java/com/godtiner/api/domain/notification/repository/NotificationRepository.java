package com.godtiner.api.domain.notification.repository;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    long countByMemberAndChecked(Member member, boolean checked);

    void deleteByMemberAndChecked(Member member,boolean checked);

    List<Notification> findByMemberAndCheckedOrderByCreatedDesc(Member member, boolean b);
}
