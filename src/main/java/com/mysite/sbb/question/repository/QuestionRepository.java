package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.repository.QuestionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer>, QuestionRepositoryCustom {

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);

    Page<Question> findByAuthorId(Pageable pageable,Long id);
}
