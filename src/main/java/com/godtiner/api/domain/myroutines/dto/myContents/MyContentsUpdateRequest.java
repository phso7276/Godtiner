package com.godtiner.api.domain.myroutines.dto.myContents;

import lombok.*;

import java.time.LocalTime;
import java.util.Optional;
import java.util.OptionalInt;

@Getter
@Setter
@NoArgsConstructor
public class MyContentsUpdateRequest {

    private Optional <String> content;
    private Optional<LocalTime> startTime;
    //종료시간
    private Optional<LocalTime> endTime;


}
