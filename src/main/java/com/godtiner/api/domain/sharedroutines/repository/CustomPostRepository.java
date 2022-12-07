package com.godtiner.api.domain.sharedroutines.repository;


import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPostRepository {

    Page<SharedRoutines> search(SearchCondition postSearchCondition, Pageable pageable);

    List<SharedRoutines> getSharedRoutinesByTagName(String tagName);
}
