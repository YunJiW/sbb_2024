package com.mysite.sbb.recommand;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/question/vote")
@RequiredArgsConstructor
@Slf4j
public class QuestionRecoController {

    private final QuestionRecoService questionRecoService;
    private final QuestionService questionService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        log.info("추천 시작");

        Question question = questionService.getQuestion(id);
        SiteUser user = userService.getUser(principal.getName());

        QuestionReco Reco = questionRecoService.findReco(user, question);
        if (Reco != null) {
            questionRecoService.delete(Reco);
        } else {
            questionRecoService.insert(user, question);
        }

        log.info("추천 종료");

        return String.format("redirect:/question/detail/%s", id);
    }

}
