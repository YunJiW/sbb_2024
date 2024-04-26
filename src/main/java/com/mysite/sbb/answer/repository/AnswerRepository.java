package com.mysite.sbb.answer.repository;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    Page<Answer> findAllByQuestion(Question question, Pageable pageable);
}
