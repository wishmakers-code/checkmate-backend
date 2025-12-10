package com.checkmate.checkmate_backend.modules.masterprofile.repository;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.Experience;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    List<Experience> findByMasterProfileOrderByStartDateDesc(MasterProfile masterProfile);

    List<Experience> findByMasterProfileIdOrderByStartDateDesc(Long masterProfileId);
}
