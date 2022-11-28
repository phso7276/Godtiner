package com.godtiner.api.domain.sharedroutines.dto;

import com.godtiner.api.domain.sharedroutines.RoutineTag;
import com.godtiner.api.domain.sharedroutines.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ApiModel(value = "태그 조회 요청")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private long id;

    private String tagName;

    private List<RoutineTag> routineTagList = new ArrayList<>();

    public Tag toEntity(TagDto req){
       return new Tag(
               req.tagName,
               req.routineTagList.stream().map(i -> new RoutineTag(i.getTag(),i.getSharedRoutine())).collect(toList())
       );
    }
}
