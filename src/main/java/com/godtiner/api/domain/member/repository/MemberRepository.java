package com.godtiner.api.domain.member.repository;

import com.godtiner.api.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Member> findByRefreshToken(String refreshToken);
}
