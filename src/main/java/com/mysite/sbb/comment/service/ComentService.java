package com.mysite.sbb.comment.service;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.comment.Coment;
import com.mysite.sbb.comment.repository.ComentRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ComentService {

    private final ComentRepository comentRepository;


    @Transactional
    public Coment createQuestionComent(Question question, String content, SiteUser author){
        Coment coment = new Coment(content, LocalDateTime.now(),author);
        coment.AddQuestion(question);

        comentRepository.save(coment);
        return coment;
    }

    @Transactional
    public Coment createAnswerComent(Answer answer,String content,SiteUser author){
        Coment coment = new Coment(content, LocalDateTime.now(),author);
        coment.AddAnswer(answer);

        comentRepository.save(coment);
        return coment;
    }

    @Transactional
    public void modify(Coment coment,String content){
        coment.updateComent(content);
    }

    @Transactional
    public void delete(Coment coment){
        comentRepository.delete(coment);
    }

}
