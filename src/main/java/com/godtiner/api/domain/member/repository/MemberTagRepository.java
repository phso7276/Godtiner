package com.godtiner.api.domain.member.repository;


import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.member.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberTagRepository extends JpaRepository<MemberTag,Long> {

    List<MemberTag> findByMember(Member member);

}
