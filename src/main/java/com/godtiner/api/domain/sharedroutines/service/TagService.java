package com.godtiner.api.domain.sharedroutines.service;


import com.godtiner.api.domain.sharedroutines.dto.TagInfo;
import com.godtiner.api.domain.sharedroutines.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class TagService {

    private final TagRepository tagRepository;

}
