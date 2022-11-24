package com.godtiner.api.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateDto {

    private Optional<String> name;
    private Optional<String> nickname;
}
