package com.mysite.sbb;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import com.mysite.sbb.question.service.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    private final UserService userService;


    @PostConstruct
    public void init() {
        Question question = new Question();
        question.setSubject("스프링부트 모델 질문입니다.");
        question.setContent("자동으로 되나요?");
        question.setCreateDate(LocalDateTime.now());

        questionRepository.save(question);

        //테스트용회원
        userService.create("admin","ss@naver.com","1234");
        SiteUser admin = userService.getUser("admin");

        for (int idx = 1; idx <= 300; idx++) {
            String subject = String.format("테스트 데이터 입니다:[%03d]", idx);
            String content = "내용무";
            questionService.create(subject, content,admin);
        }



    }
}
