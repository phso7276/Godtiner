package org.zerock.jpaweb.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.jpaweb.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    @EntityGraph(attributePaths = {"roleSet"},type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.fromSocial = :social and m.email=:email")
    Optional<Member> findByEmail(String email, boolean social);
}

