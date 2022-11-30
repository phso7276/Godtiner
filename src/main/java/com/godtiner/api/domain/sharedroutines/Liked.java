package com.godtiner.api.domain.sharedroutines;

import com.godtiner.api.domain.BaseEntity;
import com.godtiner.api.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.Table;


import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Liked extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sharedRoutineId", nullable = false)
    private SharedRoutines sharedRoutine;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @Builder
    public Liked(SharedRoutines sharedRoutines, Member member){
        this.sharedRoutine = sharedRoutines;
        this.member =member;
    }
}
