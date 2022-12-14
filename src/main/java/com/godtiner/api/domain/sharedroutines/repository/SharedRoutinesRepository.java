package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.sharedroutines.QSharedContents;
import com.godtiner.api.domain.sharedroutines.RoutineTag;
import com.godtiner.api.domain.sharedroutines.SharedContents;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface SharedRoutinesRepository extends JpaRepository<SharedRoutines, Long>, CustomPostRepository {

    //작성자 정보도 함께
    @Query("select p from SharedRoutines p join fetch p.writer where p.id = :id")
    Optional<SharedRoutines> findByIdWithMember(Long id);

    Optional<SharedRoutines> findBySharedContentsList(SharedContents sharedContents);

    List<SharedRoutines> findTop2ByRoutineTagsOrderByAvgPreferenceAsc(RoutineTag routineTag);


}
