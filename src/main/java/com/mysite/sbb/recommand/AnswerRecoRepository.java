package com.mysite.sbb.recommand;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRecoRepository extends JpaRepository<AnswerReco,Integer> {

    Optional<AnswerReco> findByUserIdAndAnswerId(Long userId, Integer answerId);
}
