package com.godtiner.api.domain.sharedroutines.dto;

import com.godtiner.api.domain.sharedroutines.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagInfo {

    private Long id;

    private String tagName;

    public TagInfo(Tag tag) {
        this.id=tag.getId();
        this.tagName=tag.getTagName();
    }

    @Builder
    public static TagInfo toDto(Tag tag) {
        return new TagInfo(tag.getId(), tag.getTagName());
    }
}
