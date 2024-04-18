package com.mysite.sbb.question;

import com.mysite.sbb.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return questionRepository.findAll();
    }

    public Question getQuestion(Integer id){
        Optional<Question> findQuestion = questionRepository.findById(id);
        if(findQuestion.isPresent()){
            return findQuestion.get();
        }else{
            throw new DataNotFoundException("question not found");
        }
    }
}
