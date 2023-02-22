package com.godtiner.api.domain.mission.service;


import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.exception.MemberException;
import com.godtiner.api.domain.member.exception.MemberExceptionType;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.mission.MemberMission;
import com.godtiner.api.domain.mission.Mission;
import com.godtiner.api.domain.mission.MissionCompleteEvent;
import com.godtiner.api.domain.mission.dto.MissionSuccessInfo;
import com.godtiner.api.domain.mission.repository.MemberMissionRepository;
import com.godtiner.api.domain.mission.repository.MissionRepository;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.domain.notification.service.NotificationService;
import com.godtiner.api.global.exception.MyRoutinesException;
import com.godtiner.api.global.exception.MyRoutinesExceptionType;
import com.godtiner.api.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class MissionService {
    @PersistenceContext
    private EntityManager em;

    private final MyRoutinesRepository myRoutinesRepository;
    private final MissionRepository missionRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;

    private final NotificationService notificationService;


    public void checkCondition(MyContents myContents){
        AuditReader auditReader = AuditReaderFactory.get(em);

        MyRoutines myRoutines = myRoutinesRepository.findMyRoutinesByMyContentsList(myContents)
                .orElseThrow(()->new MyRoutinesException(MyRoutinesExceptionType.MY_ROUTINES_NOT_FOUND));

        AuditQuery query= auditReader.createQuery()
                .forRevisionsOfEntity(MyContents.class,false,true)
                //.addProjection(AuditEntity.revisionNumber().distinct())
                .addProjection(AuditEntity.property("id").distinct())
                .add(AuditEntity.property("isClear").eq(true))
                .add(AuditEntity.property("myRoutines").eq(myRoutines))
                .add(AuditEntity.revisionType().eq(RevisionType.MOD))
                //.addProjection(AuditEntity.id().count())
               ;

       List<?> resultList = query.getResultList();

       log.info("result: "+resultList);

        log.info("result0:"+resultList.stream().count());
       /* for (Object l : resultList) { // iterator를 이용해 리스트에 값 출력
            log.info(l + "\t");
        }*/


        Optional<Mission> mission = missionRepository.findByQualification(resultList.stream().count());


        if(mission.isPresent()) {
            Optional<MemberMission> memberMission = memberMissionRepository.findMemberMissionByMissionAndMember(mission.get(), myRoutines.getWriter());
            if (memberMission.isEmpty()) {
                memberMissionRepository.save(new MemberMission(mission.get(), myRoutines.getWriter(), mission.get().getMissionName()));
                log.info("missionName:" + mission.get().getMissionName());
                eventPublisher.publishEvent(new MissionCompleteEvent(mission.get()));
                //notificationService.send(myRoutines.getWriter(), mission.get(), mission.get().getMissionName());
            }
        }

    }

    public List<MissionSuccessInfo> getSuccess() throws Exception {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() ->  new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));


        return memberMissionRepository.findAllByMember(member).stream().map(MissionSuccessInfo::new).collect(Collectors.toList());
    }
}
