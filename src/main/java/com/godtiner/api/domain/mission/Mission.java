package com.godtiner.api.domain.mission;

import com.godtiner.api.domain.sharedroutines.RoutineTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String missionName;

    private Long qualification;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mission",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MemberMission> memberMissionList;
}
