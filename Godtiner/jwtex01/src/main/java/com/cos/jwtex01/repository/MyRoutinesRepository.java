package com.cos.jwtex01.repository;

import com.cos.jwtex01.entity.MyRoutines;
import com.cos.jwtex01.entity.SharedRoutines;
import com.cos.jwtex01.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyRoutinesRepository extends JpaRepository<MyRoutines, Long> {

    @EntityGraph(attributePaths = "writer", type= EntityGraph.EntityGraphType.LOAD)
    @Query("select n from MyRoutines n where n.id=:id")
    Optional<MyRoutines> getWithWriter(Long id);

/*    Optional<MyRoutines> findByUser(User user);*/
}
