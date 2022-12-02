package com.godtiner.api.domain.myroutines.dto.myContents;

import lombok.*;

import java.time.LocalTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class MyContentsUpdateRequest {

    private Optional <String> content;
    private Optional<LocalTime> startTime;
    //종료시간
    private Optional<LocalTime> endTime;

    private Optional<Boolean> mon;
    private Optional<Boolean> tue;
    private Optional<Boolean> wed;
    private Optional<Boolean> thu;
    private Optional<Boolean> fri;
    private Optional<Boolean> sat;
    private Optional<Boolean> sun;


}
