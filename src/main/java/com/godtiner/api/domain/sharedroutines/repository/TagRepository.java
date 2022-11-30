package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository  extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);
}
