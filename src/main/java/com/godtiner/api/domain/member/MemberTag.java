package com.godtiner.api.domain.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.Tag;
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
public class MemberTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Member member;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "tagId")
    private Tag tag;

    private String tagName;

    public MemberTag(Tag tag,Member member,String tagName) {
        this.tag=tag;
        this.member=member;
        this.tagName=tagName;

    }


}
