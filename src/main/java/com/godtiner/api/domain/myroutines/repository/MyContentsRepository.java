package com.godtiner.api.domain.myroutines.repository;

import com.godtiner.api.domain.myroutines.MyContents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyContentsRepository extends JpaRepository<MyContents, Long>,RevisionRepository<MyContents, Long, Integer> {
    /*@Query("select c from MyContents c left join fetch c.myRoutines where c.id = :id")
    Optional<MyContents> findWithParentById(Long id);*/

    @Query("select c from MyContents c where c.myRoutines.id = :rid order by c.myRoutines.id asc nulls first, c.id asc")
    List<MyContents> findAllWithContentsByRidOrderByRidAscNullsFirstContentIdAsc(Long rid);
}
