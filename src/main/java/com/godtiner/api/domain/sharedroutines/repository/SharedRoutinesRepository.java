package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.sharedroutines.RoutineTag;
import com.godtiner.api.domain.sharedroutines.SharedContents;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.LikedPageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface SharedRoutinesRepository extends JpaRepository<SharedRoutines, Long>, CustomPostRepository {

    //작성자 정보도 함께
    @Query("select p from SharedRoutines p join fetch p.writer where p.id = :id")
    Optional<SharedRoutines> findByIdWithMember(Long id);

    List<SharedRoutines> findByWriter(Member member);

    Optional<SharedRoutines> findBySharedContentsList(SharedContents sharedContents);

    List<SharedRoutines> findTop2ByRoutineTagsOrderByAvgPreferenceAsc(RoutineTag routineTag);

    @Query(value ="select "+
            "new com.godtiner.api.domain.sharedroutines.dto.LikedPageDto(b.id,b.feed_thumbnail_filename,b.title, s.id) "
            +"from SharedRoutines b left join Liked s on s.sharedRoutine=b where s.member =:member")
    List<LikedPageDto> getSharedRoutinesByMemberWithLiked(@Param("member")Member member);


}
