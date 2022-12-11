package com.godtiner.api.domain.notification;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.godtiner.api.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String link;

    private String message;

    private boolean checked;

    @ManyToOne
    @JsonBackReference
    private Member member;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public static Notification from(String missionName, Member member,LocalDateTime created, boolean checked,NotificationType missionClear) {
        Notification notification = new Notification();
        notification.title=missionName;
        notification.member=member;
        notification.created = created;
        notification.checked=checked;
        notification.notificationType=missionClear;
        return notification;
    }

    public void read() {
        this.checked = true;
    }
}
