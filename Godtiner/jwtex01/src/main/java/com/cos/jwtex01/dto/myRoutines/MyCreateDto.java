package com.cos.jwtex01.dto.myRoutines;

import com.cos.jwtex01.entity.MyContents;
import com.cos.jwtex01.entity.MyRoutines;
import com.cos.jwtex01.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MyCreateDto {

    private Long id;
    private Long writerid;
    private User writer;
    private String title;
    private List<MyContents> myContentsList = new ArrayList<>();

    public MyCreateDto(MyRoutines myRoutines){
        this.id=myRoutines.getId();
        this.writerid=myRoutines.getWriter().getId();
        this.title = myRoutines.getTitle();
        this.myContentsList=myRoutines.getMyContentsList();
    }

    public MyRoutines toEntity() {
        return MyRoutines.builder()
                .id(id)
                .title(title)
                .writer(writer)
                .myContentsList(myContentsList)
                .build();
    }
}
