package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedRoutinesRepository extends JpaRepository<SharedRoutines, Long> {
}
