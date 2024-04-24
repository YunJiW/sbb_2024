package com.mysite.sbb.recommand;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRecoRepository extends JpaRepository<QuestionReco,Integer> {

    Optional<QuestionReco> findByUserIdAndQuestionId(Long userId,Integer questionId);
}
