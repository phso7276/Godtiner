package com.cos.jwtex01.service;

import com.cos.jwtex01.dto.myRoutines.MyCreateDto;
import com.cos.jwtex01.dto.sharedRoutines.SharedRoutinesDTO;
import com.cos.jwtex01.entity.MyRoutines;
import com.cos.jwtex01.entity.SharedRoutines;
import com.cos.jwtex01.entity.User;
import com.cos.jwtex01.repository.MyRoutinesRepository;
import com.cos.jwtex01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class MyRoutinesService {

    private final MyRoutinesRepository myRoutinesRepository;
    private final UserRepository userRepository;

    public MyRoutines searchMyRoutines(Long id) {
        return myRoutinesRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    public MyRoutines createMyRoutines(MyRoutines myRoutines) {
        return myRoutinesRepository.save(myRoutines);
    }

   /* public MyRoutines findRoutinesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(null);
        MyRoutines myRoutines = myRoutinesRepository.findByUser(user).orElse(null);
        *//*if (myRoutines != null) {
            changeClubStatus(club);
        }*//*
        return myRoutines;
    }*/
   /* public MyCreateDto createMyRoutines(Long userid) {
        MyRoutines myRoutines;
        Optional<MyRoutines> result = myRoutinesRepository.getWithWriter(userid);
        if(result.isPresent()){
            return myRoutinesRepository.save(myRoutines);
        }
        return null;
    }*/
}
