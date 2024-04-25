package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.dto.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {

    Page<Question> search(String subject, Pageable pageable);
}
