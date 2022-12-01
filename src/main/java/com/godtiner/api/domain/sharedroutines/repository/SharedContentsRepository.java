package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.sharedroutines.SharedContents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedContentsRepository extends JpaRepository<SharedContents, Long> {
}
