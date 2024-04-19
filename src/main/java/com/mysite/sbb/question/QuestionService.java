package com.mysite.sbb.question;

import com.mysite.sbb.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;


    public void create(String subject, String content) {
        Question q = new Question(subject, content, LocalDateTime.now());
        questionRepository.save(q);

    }

    public List<Question> getList() {
        return questionRepository.findAll();
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
