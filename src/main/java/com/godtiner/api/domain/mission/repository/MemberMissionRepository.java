package com.godtiner.api.domain.mission.repository;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.mission.MemberMission;
import com.godtiner.api.domain.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission,Long> {

    Optional<MemberMission> findMemberMissionByMission(Mission mission);

    Optional<MemberMission> findMemberMissionByMember(Member member);

    List<MemberMission> findAllByMember(Member member);

    Optional<MemberMission> findMemberMissionByMissionAndMember(Mission mission, Member member);
}
