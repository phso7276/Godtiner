package com.godtiner.api.domain.sharedroutines.dto;

import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikedPageDto {


    private Long sharedId;
    private String feed_thumbnail;
    private String detail_thumbnail;
    private String title;
    private Long likedId;


   /* public LikedPageDto(Long sharedId, String feed_thumbnail, String title, Long likedId) {
        this.sharedId=sharedId;
        this.feed_thumbnail=feed_thumbnail;
        this.title=title;
        this.likedId=likedId;

    }*/
}
