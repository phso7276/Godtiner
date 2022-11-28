package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.sharedroutines.RoutineTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineTagRepository extends JpaRepository<RoutineTag,Long> {
}
