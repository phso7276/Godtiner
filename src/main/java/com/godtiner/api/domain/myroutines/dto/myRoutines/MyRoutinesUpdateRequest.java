package com.godtiner.api.domain.myroutines.dto.myRoutines;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Optional;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "게시글 수정 요청")
public class MyRoutinesUpdateRequest {

    @ApiModelProperty(value = "게시글 제목", notes = "게시글 제목을 입력해주세요", required = true, example = "my title")
    private Optional<String> title;




}
