package com.mysite.sbb.answer.service;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.repository.AnswerRepository;
import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(author);

        answerRepository.save(answer);

        return answer;
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = answerRepository.findById(id);

        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public Page<Answer> getList(Question question, int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createDate"));
        Pageable pageable = PageRequest.of(page, 5);
        return answerRepository.findByQuestion(question, pageable);
    }

    public Page<Answer> getUserAnswerList(int page, Long id) {
        PageRequest pageRequest = PageRequest.of(page, 5);

        return answerRepository.findByAuthorId(pageRequest, id);
    }

    @Transactional
    public void modify(Answer answer, String content) {
        answer.update(content);
    }

    @Transactional
    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

}
