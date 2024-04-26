package com.mysite.sbb.answer.repository;

import com.mysite.sbb.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {

}
