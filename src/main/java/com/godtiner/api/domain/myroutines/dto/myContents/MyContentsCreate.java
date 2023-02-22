package com.godtiner.api.domain.myroutines.dto.myContents;

import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutineRules;
import com.godtiner.api.global.exception.MyContentsException;
import com.godtiner.api.global.exception.MyContentsExceptionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyContentsCreate {

    @ApiModelProperty(hidden = true)
    @Null
    private Long routineId;
    private String content;

    //private int idx;

    private LocalTime startTime;
    private LocalTime endTime;

    @ApiModelProperty(value = "규칙", notes = "규칙 내용을 추가해주세요.")
    private List<MyRoutineRules> myRulesList = new ArrayList<>();

 /*   public MyContentsCreate(String s) {
        this.content=s;
    }
*/
/*    //루틴 순번
    private int idx;

    //시작 시간
    private LocalTime startTime;
    //종료시간
    private LocalTime endTime;*/

   /* public MyContents toEntity(
            MyContentsCreate req, MyRoutinesRepository myRoutinesRepository
            ){
        return new MyContents(
                req.content,
                myRoutinesRepository.findById(req.getRoutineId()).orElseThrow(()->new MyRoutinesException(MyRoutinesExceptionType.MY_ROUTINES_NOT_FOUND))


        );
    }*/

    public static MyContents toEntity(MyContentsCreate req,
                                      Long id, MyRoutinesRepository myRoutinesRepository) {
        /*return MyContents.builder().
                content(content).
                startTime(startTime).
                endTime(endTime).
                build();*/

        return new MyContents(
                req.content,req.startTime,req.endTime,
                myRoutinesRepository.findById(id)
                        .orElseThrow(() -> new MyContentsException(MyContentsExceptionType.CONTENTS_NOT_FOUND)),
                req.myRulesList.stream().map(i -> new MyRoutineRules(i.getMyContentsId(),i.isMon(),i.isTue(),
                        i.isWed(),i.isThu(),i.isFri(),i.isSat(),i.isSun())).collect(toList())
        );
    }
}
