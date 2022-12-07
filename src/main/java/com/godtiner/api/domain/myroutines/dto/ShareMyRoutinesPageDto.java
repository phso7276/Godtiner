package com.godtiner.api.domain.myroutines.dto;


import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.Tag;
import com.godtiner.api.domain.sharedroutines.dto.TagInfo;
import com.godtiner.api.domain.sharedroutines.repository.TagRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ShareMyRoutinesPageDto {

    private List<TagInfo> tagList = new ArrayList<>();

    private List<MyContentsSimple> sharedContentsSimples = new ArrayList<>();

    public ShareMyRoutinesPageDto(MyRoutines myRoutines, TagRepository tagRepository) {
        this.tagList= tagRepository.findAll().stream().map(TagInfo::new).collect(Collectors.toList());
        this.sharedContentsSimples = myRoutines.getMyContentsList().stream().map(MyContentsSimple::new).collect(Collectors.toList());

    }

}
