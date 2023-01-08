package com.cos.jwtex01;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


import com.cos.jwtex01.entity.SharedContents;
import com.cos.jwtex01.service.SharedRoutinesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Test1 {

	@Autowired
	private SharedRoutinesService service;

	@Test
	public void 컬렉션_테스트() {
			String[] str = {"ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER"};
			List<String> list = Arrays.asList(str);
			for (String s : list) {
				System.out.println(s);
			}
	}

	@Test
	public void insertDummies(){
		//1-49 user
		IntStream.rangeClosed(1,10).forEach(i->{
			SharedContents user = SharedContents.builder()
					.content("루틴 세부항목"+i+"@rmail.com")
					.idx(i)
					.build();

			//default Role
          /* user.setRoles("USER");
            if(i>17){
                member.addMemberRole(MemberRole.MANAGER);
            }
            if(i>19){
                member.addMemberRole(MemberRole.ADMIN);
            }*/


		});
	}

	/*@Test
	public void testList(){

		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(2).build();

		PageResultDTO<SharedRoutinesDTO, SharedRoutines> resultDTO = service.getList(pageRequestDTO);

		for(SharedRoutinesDTO sharedRoutinesDTO: resultDTO.getDtoList()){
			System.out.println(sharedRoutinesDTO);
		}
	}*/
}
