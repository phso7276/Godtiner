package com.godtiner.api.domain.sharedroutines.dto.sharedRoutines;

import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesCreateRequest;
import com.godtiner.api.domain.myroutines.dto.myRoutines.MyRoutinesDto;
import com.godtiner.api.domain.sharedroutines.RoutineTag;
import com.godtiner.api.domain.sharedroutines.SharedContents;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.Tag;
import com.godtiner.api.domain.sharedroutines.repository.TagRepository;
import com.godtiner.api.global.exception.MemberNotFoundException;
import com.godtiner.api.global.util.security.SecurityUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@ApiModel(value = "공유 루틴 생성 요청")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedRoutinesCreate {

    @ApiModelProperty(value = "공유 루틴 이름", notes = "공유 루틴 이름을 입력해주세요", required = true, example = "내 루틴")
    private String title;

    @ApiModelProperty(hidden = true)
    @Null
    private Long memberId;

    @ApiModelProperty(value = "공유 루틴 소개글",notes = "소개글을 입력해주세요")
    private String routine_content;

    @ApiModelProperty(value = "내용", notes = "루틴 내용을 추가해주세요.")
    private List<SharedContents> sharedContentsList = new ArrayList<>();

    @ApiModelProperty(value = "내용", notes = "루틴 내용을 추가해주세요.")
    private List<Tag> tagList = new ArrayList<>();

    @ApiModelProperty(value = "태그", notes = "루틴 태그를 추가해주세요.")
    private List<RoutineTag> routineTagList = new ArrayList<>();

    //private Optional<MultipartFile> image;

    public static SharedRoutines toEntity(SharedRoutinesCreate req,
                                          MemberRepository memberRepository
                                          ) {
        return new SharedRoutines(
                req.title,
                req.routine_content,
                memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(MemberNotFoundException::new),
                req.sharedContentsList.stream().map(i -> new SharedContents(i.getContent(),i.getStartTime(),
                       i.getEndTime(),i.getSharedRoutine(),i.getSharedRoutineRules())).collect(toList())
                //req2.getMyContentsList().stream().map(i-> new SharedContents(i.getContent(),i.getStartTime(),
                        //i.getEndTime(),req.sharedContentsList.get(0).getSharedRoutine())).collect(toList()),
                //req.routineTagList.stream().map(i -> new RoutineTag(i.getTag(),i.getSharedRoutine())).collect(toList())
                //req.getImage().get().getOriginalFilename()
        );
    }
}