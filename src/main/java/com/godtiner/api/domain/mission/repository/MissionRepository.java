package com.godtiner.api.domain.mission.repository;

import com.godtiner.api.domain.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission,Long> {

    Optional<Mission> findByQualification(Long cnt);
}
