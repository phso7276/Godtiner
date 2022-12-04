package com.godtiner.api.domain.myroutines;

import com.godtiner.api.domain.BaseEntity;
import com.godtiner.api.domain.myroutines.dto.myRules.MyRoutineRulesUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyRoutineRules extends BaseEntity {

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MyContents myContentsId;

   /* public void confirmMyContents(MyContents myContents){
        this.myContentsId =myContents;
        myContents.addMyRules(this);
    }*/

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


    public MyRoutineRules(MyContents contents, boolean mon, boolean tue, boolean wed,
                          boolean thu, boolean fri, boolean sat, boolean sun ) {
        this.myContentsId = contents;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat =sat;
        this.sun = sun;
    }

    public void initMyContents(MyContents myContents) { // 4
        if(this.myContentsId == null) {
            this.myContentsId = myContents;
        }
    }
    public void confirmMyContents(MyContents myContents){
        this.myContentsId = myContents;
        myContents.addMyRules(this);
    }

    public void updateMon(boolean mon){
        this.mon=mon;
    }
    public void updateTue(boolean tue){
        this.tue=tue;
    }
    public void updateWed(boolean wed){
        this.wed=wed;
    }
    public void updateThu(boolean thu){
        this.thu=thu;
    }
    public void updateFri(boolean fri){
        this.fri=fri;
    }
    public void updateSat(boolean sat){
        this.sat=sat;
    }
    public void updateSun(boolean sun){
        this.sun=sun;
    }





}
