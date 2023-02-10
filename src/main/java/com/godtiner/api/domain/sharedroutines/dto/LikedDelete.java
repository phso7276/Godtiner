package com.godtiner.api.domain.sharedroutines.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikedDelete {
    private Long[] likedIdList;
}
