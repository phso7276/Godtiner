package com.godtiner.api.domain.sharedroutines.dto;


import com.godtiner.api.domain.member.MemberTag;
import com.godtiner.api.domain.member.repository.MemberTagRepository;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.MyContentsSimple;
import com.godtiner.api.domain.sharedroutines.RoutineTag;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutinesSimple;
import com.godtiner.api.domain.sharedroutines.repository.SharedRoutinesRepository;
import com.godtiner.api.domain.sharedroutines.repository.TagRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RecommendationPageDto {

    private List<MemberTag> memberInterest= new ArrayList<>();

    private List<SharedRoutinesSimple> recommendList1 = new ArrayList<>();

    private List<SharedRoutinesSimple> recommendList2 = new ArrayList<>();

    /*public RecommendationPageDto(RoutineTag routineTag1,RoutineTag routineTag2, SharedRoutinesRepository sharedRoutinesRepository) {

        this.recommendList1= sharedRoutinesRepository.findTop2ByRoutineTagsOrderByAvgPreferenceAsc(routineTag1)
                .stream().map(SharedRoutinesSimple::new).collect(Collectors.toList());
        this.recommendList2= sharedRoutinesRepository.findTop2ByRoutineTagsOrderByAvgPreferenceAsc(routineTag2)
                .stream().map(SharedRoutinesSimple::new).collect(Collectors.toList());
    }*/

    public RecommendationPageDto(List<MemberTag> memberTags,List<SharedRoutines> result1,List<SharedRoutines> result2) {
        this.memberInterest= memberTags;
        this.recommendList1= result1.stream().map(SharedRoutinesSimple::new).collect(Collectors.toList());
        this.recommendList2= result2.stream().map(SharedRoutinesSimple::new).collect(Collectors.toList());
    }
}
