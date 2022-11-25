package com.godtiner.api.domain.myroutines.dto.myContents;

import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.repository.MyContentsRepository;
import com.godtiner.api.domain.myroutines.repository.MyRoutinesRepository;
import com.godtiner.api.global.exception.MyRoutinesException;
import com.godtiner.api.global.exception.MyRoutinesExceptionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.validation.constraints.Null;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyContentsCreate {

    @ApiModelProperty(hidden = true)
    @Null
    private Long routineId;
    private String content;

    private LocalTime startTime;
    private LocalTime endTime;

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

    public MyContents toEntity() {
        return MyContents.builder().
                content(content).
                startTime(startTime).
                endTime(endTime).
                build();
    }
}
