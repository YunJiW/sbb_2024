package com.mysite.sbb.recommand.controller;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.recommand.AnswerReco;
import com.mysite.sbb.recommand.service.AnswerRecoService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/answer/vote")
public class AnswerRecoController {

    private final AnswerRecoService answerRecoService;

    private final AnswerService answerService;
    private final UserService userService;

    /**
     * 추천을 받았을때
     * 1. 추천을 한적 있는지 확인한다.
     * 2-1. 추천을 한적이 없는 경우 repository에 추가한다/
     * 2-2. 추천을 한적이 있는 경우 추천을 제거한다.
     * 값을 반환해준다.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String AnswerVote(Principal principal, @PathVariable("id") Integer id) {
        log.info("답변 추천 시작");

        Answer answer = answerService.getAnswer(id);
        SiteUser user = userService.getUser(principal.getName());

        AnswerReco reco = answerRecoService.findReco(user, answer);
        if (reco != null) {
            answerRecoService.delete(reco);
        } else {
            answerRecoService.insert(answer, user);
        }

        log.info("답변 추천 종료");
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(),answer.getId());
    }
}
