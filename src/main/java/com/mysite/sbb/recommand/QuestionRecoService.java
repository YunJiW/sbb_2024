package com.mysite.sbb.recommand;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.recommand.QuestionReco;
import com.mysite.sbb.recommand.repository.QuestionRecoRepository;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionRecoService {

    private final QuestionRecoRepository questionRecoRepository;

    @Transactional
    public void insert(SiteUser user, Question question){
        QuestionReco questionReco = new QuestionReco();
        questionReco.setUser(user);
        questionReco.setQuestion(question);

        questionRecoRepository.save(questionReco);
    }

    @Transactional
    public void delete(QuestionReco questionReco){
        questionRecoRepository.delete(questionReco);
    }

    public QuestionReco findReco(SiteUser user, Question question){
        Optional<QuestionReco> checking = questionRecoRepository.findByUserIdAndQuestionId(user.getId(), question.getId());

        if(checking.isPresent()){
            return checking.get();
        }

        return null;

    }
}
