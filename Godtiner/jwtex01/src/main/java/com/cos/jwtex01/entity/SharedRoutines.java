package com.cos.jwtex01.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedRoutines extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    private String title;
    //private String tags;
    private float avgPreference;
    private String content;

/*    @CreatedDate
    @Column(name ="regdate",updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;*/

    //조회수
    @Column(name="hits", nullable=true)
    @ColumnDefault("0")
    private int hits;

    //찜하기
    @Column(name="likecnt", nullable=true)
    @ColumnDefault("0")
    private int likecnt;

    //스크랩
    @Column(name="pickcnt", nullable=true )
    @ColumnDefault("0")
    private int pickcnt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sharedRoutines",cascade = CascadeType.ALL)
    private List<SharedContents> sharedContentsList = new ArrayList<>();

    //태그
    @Column(name="tags")
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sharedRoutines", cascade = CascadeType.ALL)
    private List<RoutineTags> tagList= new ArrayList<>();

    //이미지
    @Column(name="stored_filename", nullable=true)
    private String stored_filename;

    @Column(name="filesize", nullable=true)
    @ColumnDefault("0")
    private long filesize;

    @Column(length = 2000)
    private String thumbnailUrl;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

/*
    public List<String> getThemaList(){
        if(this.tagsList.length() > 0){
            return Arrays.asList(this.tags.split(","));
        }
        return new ArrayList<>();
    }*/


    public void changeTitle(String title){
        this.title =title;
    }
    public void changeContent(String content){
        this.content =content;
    }
}
