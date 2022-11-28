package com.godtiner.api.domain.sharedroutines;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sharedRoutineId")
    private SharedRoutines sharedRoutine;

    @ManyToOne
    @JoinColumn(name = "tagId")
    private Tag tag;

    public RoutineTag(Tag tag,SharedRoutines sharedRoutines) {
        this.tag=tag;
        this.sharedRoutine=sharedRoutines;

    }


    public void initSharedRoutines(SharedRoutines sharedRoutines) { // 4
        if(this.sharedRoutine == null) {
            this.sharedRoutine = sharedRoutines;
        }
    }

    public void initTag(Tag tag) { // 4
        if(this.tag == null) {
            this.tag = tag;
        }
    }


}
