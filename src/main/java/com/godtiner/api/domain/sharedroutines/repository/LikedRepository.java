package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.sharedroutines.Liked;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface LikedRepository extends JpaRepository<Liked,Long> {
    Optional<Liked> findByMemberAndSharedRoutine(Member member, SharedRoutines sharedRoutines);
    //int countBySharedRoutines(SharedRoutines sharedRoutines);
}
