package com.cos.jwtex01.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedContents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length=80)
    private String content;

    //루틴 순번
    private int idx;

    //시작 시간
    private LocalTime startTime;
    //종료시간
    private LocalTime endTime;

    @OneToMany(fetch = FetchType.LAZY,mappedBy ="sharedContentsId",cascade = CascadeType.ALL)
    private List<SharedRoutineRules> sharedRoutineRules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="sharedRoutinesID")
    private SharedRoutines sharedRoutines;



}
