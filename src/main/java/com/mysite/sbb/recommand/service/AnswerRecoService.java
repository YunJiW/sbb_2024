package com.mysite.sbb.recommand.service;


import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.recommand.AnswerReco;
import com.mysite.sbb.recommand.repository.AnswerRecoRepository;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerRecoService {
    private final AnswerRecoRepository answerRecoRepository;


    //추천을 넣어주기.
    @Transactional
    public void insert(Answer answer, SiteUser siteUser) {
        AnswerReco answerReco = new AnswerReco(siteUser,answer);
        answerRecoRepository.save(answerReco);
    }

    @Transactional
    public void delete(AnswerReco answerReco) {
        answerRecoRepository.delete(answerReco);
    }

    public AnswerReco findReco(SiteUser user, Answer answer) {
        Optional<AnswerReco> check = answerRecoRepository.findByUserIdAndAnswerId(user.getId(), answer.getId());

        if (check.isPresent()) {
            return check.get();
        }
        return null;
    }
}
