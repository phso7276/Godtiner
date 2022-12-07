package com.godtiner.api.domain.sharedroutines;

import com.godtiner.api.domain.member.MemberTag;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String tagName;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RoutineTag> routineTags;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MemberTag> memberTags;

    public Tag(String tagName,List<RoutineTag> routineTags){

        this.tagName=tagName;
        this.routineTags=new ArrayList<>();
        addRoutineTags(routineTags);
    }


   /*public Tag(String tagName){

       this.tagName=tagName;
   }*/


    public void addRoutineTags(List<RoutineTag> tagList){
        tagList.stream().forEach(i -> {
            routineTags.add(i);
            i.initTag(this);
        });
    }
}
