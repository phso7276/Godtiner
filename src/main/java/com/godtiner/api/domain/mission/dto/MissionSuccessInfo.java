package com.godtiner.api.domain.mission.dto;

import com.godtiner.api.domain.mission.MemberMission;
import com.godtiner.api.domain.mission.Mission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MissionSuccessInfo {

    private Long id;

    private Long missionId;

    private String missionName;

    private boolean success;

    public MissionSuccessInfo(MemberMission memberMission){
        this.id= memberMission.getId();
        this.missionId=memberMission.getMission().getId();
        this.missionName=memberMission.getMissionName();
        this.success=true;

    }
}
