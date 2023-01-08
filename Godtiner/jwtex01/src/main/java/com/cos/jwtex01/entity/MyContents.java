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
public class MyContents {
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

    @OneToMany(fetch = FetchType.LAZY,mappedBy ="myContentsId",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyRoutineRules> myRoutineRulesList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="myRoutinesID")
    private MyRoutines myRoutines;
    private boolean isRemoved=false;


    public void confirmMyRoutines(MyRoutines myRoutines){
        this.myRoutines = myRoutines;
        myRoutines.addMyContents(this);
    }
    public void addMyRules(MyRoutineRules myRoutineRules){
        myRoutineRulesList.add(myRoutineRules);
    }

    public void updateContent(String content) {
        this.content = content;
    }
    //== 삭제 ==//
    public void remove() {
        this.isRemoved = true;
    }
}
