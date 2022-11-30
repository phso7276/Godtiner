package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface SharedRoutinesRepository extends JpaRepository<SharedRoutines, Long> {

    //작성자 정보도 함께
    @Query("select p from SharedRoutines p join fetch p.writer where p.id = :id")
    Optional<SharedRoutines> findByIdWithMember(Long id);
}
