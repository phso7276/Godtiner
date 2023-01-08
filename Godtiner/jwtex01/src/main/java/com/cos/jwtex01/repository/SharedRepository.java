package com.cos.jwtex01.repository;

import com.cos.jwtex01.entity.SharedRoutines;
import com.cos.jwtex01.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Repository
public interface SharedRepository extends JpaRepository<SharedRoutines, Long> {
    //Page<SharedRoutines> find(Pageable pageable);

    @EntityGraph(attributePaths = "writer", type= EntityGraph.EntityGraphType.LOAD)
    @Query("select n from SharedRoutines n where n.id=:id")
    Optional<SharedRoutines> getWithWriter(Long id);

    @EntityGraph(attributePaths = {"writer"}, type= EntityGraph.EntityGraphType.LOAD)
    @Query("select n from SharedRoutines n where n.writer.id=:id")
    List<SharedRoutines> getList(Long id);

}
