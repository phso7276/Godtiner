package com.godtiner.api.domain.myroutines.dto.myRoutines;

import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.global.exception.MemberNotFoundException;
import com.godtiner.api.global.util.security.SecurityUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

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

/*   @ApiModelProperty(value = "내용", notes = "루틴 내용을 추가해주세요.")
    private Optional<MyContentsCreate> myContentsList;*/

    @ApiModelProperty(value = "내용", notes = "루틴 내용을 추가해주세요.")
    private List<MyContents> myContentsList = new ArrayList<>();

/*    public MyRoutinesCreateRequest(String title, Long memberId, Optional<MyContentsCreate> myContentsList){
        this.title =title;
        this.memberId = memberId;
        this.myContentsList =myContentsList;
    }*/

/*    public MyRoutines toEntity() {
        return MyRoutines.builder().
                title(title).
                myContentsList(myContentsList).
                build();
    }*/
  public static MyRoutines toEntity(MyRoutinesCreateRequest req,
                                      MemberRepository memberRepository) {
        return new MyRoutines(
                req.title,
                memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(MemberNotFoundException::new),
                req.myContentsList.stream().map(i -> new MyContents(i.getContent(),i.getStartTime(),
                        i.getEndTime(),i.getMyRoutines(),i.getMyRoutineRulesList())).collect(toList())
        );
    }
}
