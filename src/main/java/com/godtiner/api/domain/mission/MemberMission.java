package com.godtiner.api.domain.mission;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godtiner.api.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Member member;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Mission mission;

    private String missionName;

    public MemberMission(Mission mission,Member member,String missionName) {
        this.mission=mission;
        this.member=member;
        this.missionName=missionName;

    }


}