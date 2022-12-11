package com.godtiner.api.domain.mission.controller;


import com.godtiner.api.domain.member.dto.MemberInfoDto;
import com.godtiner.api.domain.mission.service.MissionService;
import com.godtiner.api.domain.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Log4j2
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/mission")
    public Response getMyMissions() throws Exception {

        return Response.success(missionService.getSuccess());
    }
}
