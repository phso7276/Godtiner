package com.cos.jwtex01.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyRoutineRules extends BaseEntity{
    private boolean isRemoved =false;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="myContentsId")
    private MyContents myContentsId;

    public void confirmMyContents(MyContents myContents){
        this.myContentsId =myContents;
        myContents.addMyRules(this);
    }

    //수정

    public void updateRules(boolean mon,boolean tue, boolean wed,
                          boolean thu,boolean fri, boolean sat, boolean sun) {
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat =sat;
        this.sun = sun;
    }

    @Builder
    public MyRoutineRules( MyContents contents, boolean mon,boolean tue, boolean wed,
                           boolean thu,boolean fri, boolean sat, boolean sun ) {
        this.myContentsId = contents;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat =sat;
        this.sun = sun;
        this.isRemoved = false;
    }

}
