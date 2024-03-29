package com.godtiner.api.domain.sharedroutines;

import com.godtiner.api.domain.BaseEntity;
import com.godtiner.api.domain.member.Member;
import com.godtiner.api.domain.sharedroutines.dto.TagInfo;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedRoutines extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Audited(targetAuditMode = NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member writer;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String routineContent;
    //private String tags;
    @Column(nullable=true)
    private double avgPreference;

    @Convert(converter = RoutineTagConverter.class)
    private List<TagInfo> tags;



    //조회수
    @Column(name="hits", nullable=true)
    private int hits;

    //찜하기
    @Column(name="likecnt", nullable=true)
    private int likecnt;

    //스크랩
    @Column(name="pickcnt", nullable=true )
    private int pickcnt;

    @NotAudited
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sharedRoutine",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SharedContents> sharedContentsList;

    //태그

    @NotAudited
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "sharedRoutine",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RoutineTag> routineTags;

    @NotAudited
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "sharedRoutine",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Liked> likedList;


    //이미지
    //@NotAudited
    @Column(name="stored_filename", nullable=true)
    private String stored_filename;

    //@NotAudited
    @Column(name="filesize", nullable=true)
    private long filesize =0;

    //@NotAudited
    @Column(length = 2000)
    private String feed_thumbnail_filename;


    //@NotAudited
    @Column(length = 2000)
    private String detail_thumbnail_filename;

    //@NotAudited
    @Column(name = "original_file_name")
    private String originalFileName;


 /*   public SharedRoutines(String title,String routineContent,Member writer,List<SharedContents> sharedContentsList,
                          List<RoutineTag> tagList){
        this.title =title;
        this.routineContent=routineContent;
        this.writer= writer;
        this.sharedContentsList = new ArrayList<>();
        addSharedContents(sharedContentsList);
        this.routineTags = new ArrayList<>();
        addRoutineTags(tagList);

    }*/

   /* public SharedRoutines(String title,String routineContent,Member writer,List<SharedContents> sharedContentsList
                         ){
        this.title =title;
        this.routineContent=routineContent;
        this.writer= writer;
        this.sharedContentsList = new ArrayList<>();
        addSharedContents(sharedContentsList);


    }*/

    public SharedRoutines(String title, String routineContent, Member writer,
                          List<TagInfo> tagInfoList) {
        this.title =title;
        this.routineContent=routineContent;
        this.writer= writer;
        /*this.sharedContentsList = new ArrayList<>();
        addSharedContents(sharedContentsList);*/
        this.tags =tagInfoList;

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

    public void updateFeedThumbnailFilename(String filePath) {this.feed_thumbnail_filename=filePath;}

    public void updateDetailThumbnailFilename(String filepath){this.detail_thumbnail_filename =filepath;}

    public void addLikedCnt(){this.likecnt+=1;}
    public void addPickCnt(){this.pickcnt+=1;}
    public void addHits(){this.hits+=1;}

    public void deleteLikedCnt(){this.likecnt -=1;}

    public void deletePickCnt(){this.pickcnt -=1;}



    //public void addTags(String json){this.tags=json;}

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

