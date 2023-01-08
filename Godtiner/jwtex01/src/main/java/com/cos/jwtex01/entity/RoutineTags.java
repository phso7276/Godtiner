package com.cos.jwtex01.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineTags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String tag;
    //태그 순번
    private int idx;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="sharedRoutinesID")
    private SharedRoutines sharedRoutines;
}
