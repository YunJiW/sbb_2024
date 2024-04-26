package com.mysite.sbb.question.service;

import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;


    @Transactional
    public void create(String subject, String content, SiteUser author) {
        Question q = new Question(subject, content, LocalDateTime.now(), author);
        questionRepository.save(q);

    }

    @Transactional
    public void modify(Question question, String subject, String content) {
        question.update(subject, content);
    }

    @Transactional
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public Page<Question> getList(int page,String sub) {
        Pageable pageable = PageRequest.of(page, 10);


        return questionRepository.search(sub,pageable);
    }


    public Question getQuestion(Integer id) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        log.info("findById 진행");
        if (findQuestion.isPresent()) {
            return findQuestion.get();
        } else {
            log.info("찾지 못했음");
            throw new DataNotFoundException("question not found");
        }
    }

}
