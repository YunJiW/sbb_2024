package com.mysite.sbb;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final QuestionRepository questionRepository;


    @PostConstruct
    public void init() {
        Question question = new Question();
        question.setSubject("스프링부트 모델 질문입니다.");
        question.setContent("자동으로 되나요?");
        question.setCreateDate(LocalDateTime.now());

        questionRepository.save(question);
    }
}
