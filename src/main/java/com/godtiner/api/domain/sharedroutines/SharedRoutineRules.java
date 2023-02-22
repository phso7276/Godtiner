package com.godtiner.api.domain.sharedroutines;

import com.godtiner.api.domain.BaseEntity;
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
public class SharedRoutineRules extends BaseEntity {

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
    @JoinColumn(name ="sharedContents")
    private SharedContents sharedContents;

    public void initSharedContents(SharedContents sharedContents) { // 4
        if(this.sharedContents == null) {
            this.sharedContents = sharedContents;
        }
    }
}
