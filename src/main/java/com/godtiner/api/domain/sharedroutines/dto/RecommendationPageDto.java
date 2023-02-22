package com.godtiner.api.domain.sharedroutines.dto;


import com.godtiner.api.domain.member.MemberTag;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SharedRoutinesSimple;
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

    private List<SharedRoutinesSimple> item_matrix_recommend =new ArrayList<>();

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
        //this.item_matrix_recommend =result2.stream().map(SharedRoutinesSimple::new).collect(Collectors.toList());
    }

    public RecommendationPageDto(List<MemberTag> memberTags,List<SharedRoutines> result1,List<SharedRoutines> result2,
                                 List<SharedRoutines> recommendList) {
        this.memberInterest= memberTags;
        this.recommendList1= result1.stream().map(SharedRoutinesSimple::new).collect(Collectors.toList());
        this.recommendList2= result2.stream().map(SharedRoutinesSimple::new).collect(Collectors.toList());
        this.item_matrix_recommend =recommendList.stream().map(SharedRoutinesSimple::new).collect(Collectors.toList());
    }
}
