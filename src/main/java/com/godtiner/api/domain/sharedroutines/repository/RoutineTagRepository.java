package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.sharedroutines.RoutineTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoutineTagRepository extends JpaRepository<RoutineTag,Long> {


    Optional<RoutineTag> findByTagName(String tagName);
}
