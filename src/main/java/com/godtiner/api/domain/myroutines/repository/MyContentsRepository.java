package com.godtiner.api.domain.myroutines.repository;

import com.godtiner.api.domain.myroutines.MyContents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyContentsRepository extends JpaRepository<MyContents, Long>,RevisionRepository<MyContents, Long, Integer> {
    /*@Query("select c from MyContents c left join fetch c.myRoutines where c.id = :id")
    Optional<MyContents> findWithParentById(Long id);*/

    @Query("select c from MyContents c where c.myRoutines.id = :rid order by c.myRoutines.id asc nulls first, c.id asc")
    List<MyContents> findAllWithContentsByRidOrderByRidAscNullsFirstContentIdAsc(Long rid);

    @Query(value = "select c from MyContents c " +
            "left join MyRoutineRules r ON c.id=r.myContentsId.id where r.mon=true and c.myRoutines.id=:rid")
    List<MyContents> getIsMonMyContentsByRidWithMyRoutineRulesAndMyRoutines(@Param("rid")Long rid);

    @Query(value = "select c from MyContents c " +
            "left join MyRoutineRules r ON c.id=r.myContentsId.id where r.tue=true and c.myRoutines.id=:rid")
    List<MyContents> getIsTueMyContentsByRidWithMyRoutineRulesAndMyRoutines(@Param("rid")Long rid);
    @Query(value = "select c from MyContents c " +
            "left join MyRoutineRules r ON c.id=r.myContentsId.id where r.wed=true and c.myRoutines.id=:rid")
    List<MyContents> getIsWedMyContentsByRidWithMyRoutineRulesAndMyRoutines(@Param("rid")Long rid);
    @Query(value = "select c from MyContents c " +
            "left join MyRoutineRules r ON c.id=r.myContentsId.id where r.thu=true and c.myRoutines.id=:rid")
    List<MyContents> getIsThuMyContentsByRidWithMyRoutineRulesAndMyRoutines(@Param("rid")Long rid);
    @Query(value = "select c from MyContents c " +
            "left join MyRoutineRules r ON c.id=r.myContentsId.id where r.fri=true and c.myRoutines.id=:rid")
    List<MyContents> getIsFriMyContentsByRidWithMyRoutineRulesAndMyRoutines(@Param("rid")Long rid);
    @Query(value = "select c from MyContents c " +
            "left join MyRoutineRules r ON c.id=r.myContentsId.id where r.sat=true and c.myRoutines.id=:rid")
    List<MyContents> getIsSatMyContentsByRidWithMyRoutineRulesAndMyRoutines(@Param("rid")Long rid);

    @Query(value = "select c from MyContents c " +
            "left join MyRoutineRules r ON c.id=r.myContentsId.id where r.sun=true and c.myRoutines.id=:rid")
    List<MyContents> getIsSunMyContentsByRidWithMyRoutineRulesAndMyRoutines(@Param("rid")Long rid);
}
