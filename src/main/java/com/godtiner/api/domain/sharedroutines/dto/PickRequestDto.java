package com.godtiner.api.domain.sharedroutines.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PickRequestDto {


    private Long[] contentIdList;
}
