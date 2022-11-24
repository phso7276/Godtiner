package com.godtiner.api.domain.myroutines.dto;

import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.global.exception.MemberNotFoundException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@ApiModel(value = "개인 루틴 생성 요청")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyRoutinesCreateRequest {
    @ApiModelProperty(value = "루틴 이름", notes = "루틴 이름을 입력해주세요", required = true, example = "내 루틴")
    private String title;

    @ApiModelProperty(hidden = true)
    @Null
    private Long memberId;

    @ApiModelProperty(value = "내용", notes = "루틴 내용을 추가해주세요.")
    private Optional<MyContentsCreate> myContentsList;

    public MyRoutines toEntity() {
        return MyRoutines.builder().
                title(title).
                build();
    }
 /*   public static MyRoutines toEntity(MyRoutinesCreateRequest req,
                                      MemberRepository memberRepository) {
        return new MyRoutines(
                req.title,
                memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException::new),
                req.myContentsList.stream().map(i -> new MyContents(i.getIdx(), i.getContent(),i.getStartTime(),i.getEndTime())).collect(toList())
        );
    }*/
}
