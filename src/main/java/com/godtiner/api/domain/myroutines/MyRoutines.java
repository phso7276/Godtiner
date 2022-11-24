package com.godtiner.api.domain.myroutines;

import com.godtiner.api.domain.BaseEntity;

import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.myroutines.dto.MyContentsCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyRoutines extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member writer;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "myRoutines",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MyContents> myContentsList = new ArrayList<>();

    //== 내용 수정 ==//
    public void updateTitle(String title) {
        this.title = title;
    }
    //== 연관관계 편의 메서드 ==//
    public void confirmWriter(Member writer) {
        //writer는 변경이 불가능하므로 이렇게만 해주어도 될듯
        this.writer = writer;
        writer.addMyRoutines(this);
    }

  /*  public MyRoutines(String title,Member writer,List<MyContents> myContentsList){
        this.title =title;
        this.writer= writer;
        this.myContentsList =new ArrayList<>();
        addMyContents(myContentsList);
    }*/

    @Builder
    public MyRoutines(String title,Member writer,List<MyContents> myContentsList){
        this.title =title;
        this.writer= writer;

    }



   public void addMyContents(MyContents myContents){
        //comment의 Post 설정은 comment에서 함
        myContentsList.add(myContents);
    }
   /* public void addMyContents(List<MyContents> myContents){
        myContents.stream().forEach(i -> {
            myContentsList.add(i);
            i.initMyRoutines(this);
        });
    }*/
    }





