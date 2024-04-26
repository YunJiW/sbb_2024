package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.repository.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SbbApplicationTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장

        List<Question> all = questionRepository.findAll();
        assertThat(all.size()).isEqualTo(303);

        Optional<Question> byId = questionRepository.findById(303);
        if (byId.isPresent()) {
            Question q = byId.get();
            assertThat(q.getSubject()).isEqualTo("스프링부트 모델 질문입니다.");

            Answer a = new Answer();
            a.setContent("네 자동으로 생성됩니다.");
            a.setQuestion(q);
            a.setCreateDate(LocalDateTime.now());
            answerRepository.save(a);

        }


        Question findQuestion = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertThat(findQuestion.getId()).isEqualTo(302);
    }

}