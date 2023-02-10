package com.godtiner.api;


import com.godtiner.api.domain.sharedroutines.repository.LikedRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class ApiApplicationTests {

    @Autowired
    LikedRepository likedRepository;

   /* @Test
    public void testReadWithSharedRoutine(){

        Object result = likedRepository.getLikedByMemberWithSharedRoutine();

        Object[] arr = (Object[]) result;

        System.out.println("---------------");
        System.out.println(Arrays.toString(arr));
    }*/
}
