package com.peazh.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.peazh.web.dto.LoginDTO;
import com.peazh.web.service.LoginService;

@SpringBootTest
public class Aug30ApplicationTests {
	
	
	//Junit = 자바에서 독립된 단위테스트를 지원해주는 프레임워크
	//단정(assert) 메서드로 테스트 케이스의 수행결과를 판별
	// junit4이후부터는 테스트를 지원 어노케이션을 제공한다. (@Test @Before @After 등)
	// @Test 메서드가 호출할 때마다 새로운 인스턴스를 생성하여 독리적인 테스트가 이루어지게 한다.
	
	@Autowired
	LoginService loginService;
	
	@Test
	@DisplayName("처음 테스트")
	void contextLoads() {
		//DTO에 값을 담아서 DB에 물어보기
		LoginDTO dto = new LoginDTO();
		dto.setId("peazh");
		dto.setPw("00001");
		
		LoginDTO result = loginService.login(dto);
		
		// 가져온 이름이 홍길동이랑 똑같아?
		assertEquals(result.getM_name(), "한여름");
		//assertNotNull(result);// 안에 내용이 있어?
		//assertSame(dto, result);//두 개의 객체가 똑같아?
		
	}

}
