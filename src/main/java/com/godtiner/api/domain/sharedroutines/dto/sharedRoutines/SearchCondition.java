package com.godtiner.api.domain.sharedroutines.dto.sharedRoutines;

import lombok.Data;

import java.util.List;

@Data
public class SearchCondition {

    private String title;
    private String routineContent;
    private String tagName;

    private List<Long> tagId;
}
