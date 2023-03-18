package com.godtiner.api.domain.notification.service;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.mission.Mission;
import com.godtiner.api.domain.notification.Notification;
import com.godtiner.api.domain.notification.dto.NotificationInfoDto;
import com.godtiner.api.domain.notification.repository.EmitterRepository;
import com.godtiner.api.domain.notification.repository.NotificationRepository;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class NotificationService {
    public final NotificationRepository notificationRepository;
    public final MemberRepository memberRepository;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;

    @Autowired
    public NotificationService(EmitterRepository emitterRepository,NotificationRepository notificationRepository,MemberRepository memberRepository) {
        this.emitterRepository = emitterRepository;
        this.notificationRepository=notificationRepository;
        this.memberRepository=memberRepository;
    }
    public void markAsRead(List<Notification> notifications) {

        notifications.forEach(Notification::read);
    }

    public SseEmitter subscribe(String lastEventId,Long mid) {
       /* Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));*/

        Member member = memberRepository.findById(mid).orElseThrow(()-> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        // 1
        String id = member.getId() + "_" + System.currentTimeMillis();


        // 2
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        // 3
        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendToClient(emitter, id, "EventStream Created. [userId=" + member.getId() + "]");

        // 4
        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(member.getId()));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    // 3
    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }
    public void send(Member receiver, Mission mission, String content) {
        Notification notification = createNotification(receiver, mission, content);
        String id = String.valueOf(receiver.getId());

        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                    emitterRepository.saveEventCache(key, notification);
                    // 데이터 전송
                    sendToClient(emitter, key, getNotification());
                }
        );
    }


    private Notification createNotification(Member receiver, Mission mission, String content) {
        return Notification.builder()
                .member(receiver)
                .message(content)
                .title(content)
                .link("/myRoutine/clear/" + mission.getId())
                .checked(false)
                .build();
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
