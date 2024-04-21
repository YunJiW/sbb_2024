package com.mysite.sbb;

import com.mysite.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;


	@Test
	void testJpa(){

		for(int idx =1; idx <=300;idx++){
			String subject = String.format("테스트 데이터 입니다:[%03d]",idx);
			String content = "내용무";
			questionService.create(subject,content);
		}
	}
}
