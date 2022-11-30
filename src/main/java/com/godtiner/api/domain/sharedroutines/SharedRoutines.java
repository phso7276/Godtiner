package com.godtiner.api.domain.sharedroutines;

import com.godtiner.api.domain.BaseEntity;
import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.myroutines.MyContents;
import com.godtiner.api.domain.myroutines.MyRoutineRules;
import com.godtiner.api.domain.myroutines.MyRoutines;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedRoutines extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member writer;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String routineContent;
    //private String tags;
    @Column(nullable=true)
    private double avgPreference;



    //조회수
    @Column(name="hits", nullable=true)
    private int hits;

    //찜하기
    @Column(name="likecnt", nullable=true)
    private int likecnt;

    //스크랩
    @Column(name="pickcnt", nullable=true )
    private int pickcnt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sharedRoutine",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SharedContents> sharedContentsList;

    //태그


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "sharedRoutine",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RoutineTag> routineTags;

    //이미지
    @Column(name="stored_filename", nullable=true)
    private String stored_filename;

    @Column(name="filesize", nullable=true)
    private long filesize =0;

    @Column(length = 2000)
    private String thumbnailUrl;

    @Column(name = "original_file_name")
    private String originalFileName;


    public SharedRoutines(String title,String routineContent,Member writer,List<SharedContents> sharedContentsList,
                          List<RoutineTag> tagList){
        this.title =title;
        this.routineContent=routineContent;
        this.writer= writer;
        this.sharedContentsList = new ArrayList<>();
        addSharedContents(sharedContentsList);
        this.routineTags = new ArrayList<>();
        addRoutineTags(tagList);

    }

    public SharedRoutines(String title,String routineContent,Member writer,List<SharedContents> sharedContentsList
                         ){
        this.title =title;
        this.routineContent=routineContent;
        this.writer= writer;
        this.sharedContentsList = new ArrayList<>();
        addSharedContents(sharedContentsList);


    }



    public void addSharedContents(List<SharedContents> sharedContents){
        sharedContents.stream().forEach(i -> {
            sharedContentsList.add(i);
            i.initSharedRoutines(this);
        });
    }

    public void addRoutineTags(List<RoutineTag> tagList){
        tagList.stream().forEach(i -> {
            routineTags.add(i);
            i.initSharedRoutines(this);
        });
    }

    public void updateStoredFilename(String filePath) {
        this.stored_filename = filePath;
    }
    public void updateOriginalFilenmae(String filePath) {
        this.originalFileName = filePath;
    }


/*
    public List<String> getThemaList(){
        if(this.tagsList.length() > 0){
            return Arrays.asList(this.tags.split(","));
        }
        return new ArrayList<>();
    }*/
/*

    public void changeTitle(String title){
        this.title =title;
    }
    public void changeContent(String content){
        this.content =content;
    }*/
}

