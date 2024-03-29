package com.godtiner.api.domain.myroutines;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Audited
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyContents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotAudited
    @Column(length=80)
    private String content;

    @NotAudited
    //루틴 순번
    private int idx;


    //시작 시간

    @NotAudited
    private LocalTime startTime;
    //종료시간

    @NotAudited
    private LocalTime endTime;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY,mappedBy ="myContentsId",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyRoutineRules> myRoutineRulesList;

    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="myroutinesId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MyRoutines myRoutines;


    private boolean isClear=false;

/*
    public MyContents( String content,MyRoutines myRoutines) {
        this.myRoutines = myRoutines;
        this.content = content;
       */
/* this.startTime = startTime;
        this.endTime =endTime;*//*

    }
*/


   public MyContents(String content, LocalTime startTime,LocalTime endTime,MyRoutines myRoutines,
                     List<MyRoutineRules> myRules) {
        /*this.myRoutines = myRoutines;*/
        this.content = content;
        this.startTime = startTime;
        this.endTime =endTime;
        this.myRoutines = myRoutines;
       this.myRoutineRulesList = new ArrayList<>();
       addMyRules(myRules);
    }


    public MyContents(String content, LocalTime startTime, LocalTime endTime, List<MyRoutineRules> myRoutineRulesList) {
    }

    public MyContents(String content, MyRoutines myRoutines) {
       this.content=content;
       this.myRoutines=myRoutines;
    }


   /* public MyContents(String content, Member member) {
        this.content=content;
        this.
    }*/


    public void initMyRoutines(MyRoutines myRoutines) { // 4
        if(this.myRoutines == null) {
            this.myRoutines = myRoutines;
        }
    }
  public void confirmMyRoutines(MyRoutines myRoutines){
        this.myRoutines = myRoutines;
        myRoutines.addMyContents(this);
    }
    public void addMyRules(MyRoutineRules myRoutineRules){
        myRoutineRulesList.add(myRoutineRules);
    }

    public void addMyRules(List<MyRoutineRules> myRules){
        myRules.stream().forEach(i -> {
            myRoutineRulesList.add(i);
            i.initMyContents(this);
        });
    }



    public void updateContent(String content) {
        this.content = content;
    }
    public void updateStartTime(LocalTime startTime){
        this.startTime = startTime;
    }

    public void updateEndTime(LocalTime endTime){
        this.endTime = endTime;
    }
    //== 삭제 ==//
    public void clear() {
        this.isClear = true;
    }
    @Scheduled(cron="0 0 0 * * ?")
    public void cancelClear(){this.isClear=false;}

}
