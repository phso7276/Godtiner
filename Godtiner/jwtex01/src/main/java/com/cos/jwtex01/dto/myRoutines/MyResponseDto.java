package com.cos.jwtex01.dto.myRoutines;

import com.cos.jwtex01.entity.MyContents;
import com.cos.jwtex01.entity.MyRoutines;
import com.cos.jwtex01.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MyResponseDto {

    private Long id;
    private User writer;
    private String title = "내 루틴";
    private List<MyContents> myContentsList = new ArrayList<>();

    public MyResponseDto(MyRoutines myRoutines) {
        BeanUtils.copyProperties(myRoutines, this);
    }
}
