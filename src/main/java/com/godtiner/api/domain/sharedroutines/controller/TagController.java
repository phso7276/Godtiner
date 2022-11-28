package com.godtiner.api.domain.sharedroutines.controller;

import com.godtiner.api.domain.response.Response;
import com.godtiner.api.domain.sharedroutines.Tag;
import com.godtiner.api.domain.sharedroutines.repository.TagRepository;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Tag Controller", tags = "Tag")
@RestController
@RequiredArgsConstructor
@Slf4j
public class TagController {
    private final TagRepository tagRepository;

    @GetMapping("/tags")
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> reports() {
        return tagRepository.findAll();
    }
}