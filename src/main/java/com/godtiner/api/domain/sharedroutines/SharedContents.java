package com.godtiner.api.domain.sharedroutines;

import com.godtiner.api.domain.myroutines.MyRoutineRules;
import com.godtiner.api.domain.myroutines.MyRoutines;
import com.godtiner.api.domain.myroutines.dto.myRules.MyRoutineRulesDto;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedContents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length=80)
    private String content;

    //루틴 순번
    private int idx;

    //시작 시간
    private LocalTime startTime;
    //종료시간
    private LocalTime endTime;

    @OneToMany(fetch = FetchType.LAZY,mappedBy ="sharedContents",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SharedRoutineRules> sharedRoutineRules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="sharedRoutinesID")
    @JsonIgnore
    private SharedRoutines sharedRoutine;

   public SharedContents(String content, LocalTime startTime, LocalTime endTime, SharedRoutines sharedRoutines,
                          List<SharedRoutineRules> sharedRules) {
        /*this.myRoutines = myRoutines;*/
        this.content = content;
        this.startTime = startTime;
        this.endTime =endTime;
        this.sharedRoutine = sharedRoutines;
        this.sharedRoutineRules = new ArrayList<>();
        addSharedRules(sharedRules);
    }



    public SharedContents(String content, LocalTime startTime, LocalTime endTime, SharedRoutines sharedRoutines) {
        this.content = content;
        this.startTime=startTime;
        this.endTime=endTime;
        this.sharedRoutine = sharedRoutines;

    }

 /*  public SharedContents(String content, LocalTime startTime, LocalTime endTime, SharedRoutines sharedRoutines
                        ) {
       *//*this.myRoutines = myRoutines;*//*
       this.content = content;
       this.startTime = startTime;
       this.endTime =endTime;
       this.sharedRoutine = sharedRoutines;

   }*/


    public void initSharedRoutines(SharedRoutines sharedRoutines) { // 4
        if(this.sharedRoutine == null) {
            this.sharedRoutine = sharedRoutines;
        }
    }

    public void addSharedRules(List<SharedRoutineRules> sharedRules){
        sharedRules.stream().forEach(i -> {
            sharedRoutineRules.add(i);
            i.initSharedContents(this);
        });
    }

}