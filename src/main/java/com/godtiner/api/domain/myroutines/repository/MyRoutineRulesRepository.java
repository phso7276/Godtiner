package com.godtiner.api.domain.myroutines.repository;

import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutineRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyRoutineRulesRepository extends JpaRepository<MyRoutineRules,Long> {


    Optional<MyRoutineRules> findByMyContentsId(MyContents myContents);
}
