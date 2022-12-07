package com.godtiner.api.domain.sharedroutines.dto.sharedRoutines;

import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.TagInfo;
import com.godtiner.api.domain.sharedroutines.repository.TagRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.jdt.internal.compiler.ast.LabeledStatement;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class SharedPagingDto {
    private int totalPageCount;//총 몇페이지가 존재하는지
    private int currentPageNum;//현재 몇 페이지인지
    private long totalElementCount; //존재하는 게시글의 총 개수
    private int currentPageElementCount; //현재 페이지에 존재하는 게시글 수

    private List<TagInfo> tagInfoList = new ArrayList<>();
    private List<SharedRoutinesSimple> simpleLectureDtoList = new ArrayList<>();




    public SharedPagingDto(Page<SharedRoutines> searchResults, TagRepository tagRepository) {
        this.totalPageCount = searchResults.getTotalPages();
        this.currentPageNum = searchResults.getNumber();
        this.totalElementCount = searchResults.getTotalElements();
        this.currentPageElementCount = searchResults.getNumberOfElements();
        this.simpleLectureDtoList = searchResults.getContent().stream().map(SharedRoutinesSimple::new).collect(Collectors.toList());
        this.tagInfoList = tagRepository.findAll().stream().map(TagInfo::new).collect(Collectors.toList());
    }
}
