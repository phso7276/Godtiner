package com.godtiner.api.domain.myroutines.repository;


import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.myroutines.MyRoutines;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyRoutinesRepository extends JpaRepository<MyRoutines, Long>{

   /* @EntityGraph(attributePaths = "writer", type= EntityGraph.EntityGraphType.LOAD)
    @Query("select n from MyRoutines n join fetch n.writer where n.id=:id")
    Optional<MyRoutines> findByIdWithMember(Long mid);*/

    @EntityGraph(attributePaths = "writer", type= EntityGraph.EntityGraphType.LOAD)
    @Query("select n from MyRoutines n where n.writer.id=:id")
    Optional<MyRoutines> getWithWriter(Long id);

    Optional<MyRoutines> findByWriter(Member writer);
}
