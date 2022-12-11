package com.godtiner.api.domain.mission;

import lombok.Getter;

import java.util.Optional;

@Getter
public class MissionCompleteEvent {

    private final Mission mission;

    public MissionCompleteEvent(Mission mission) {
        this.mission = mission;

    }
}
