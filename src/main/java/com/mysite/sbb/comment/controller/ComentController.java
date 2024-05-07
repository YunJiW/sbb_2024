package com.mysite.sbb.comment.controller;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.comment.ComentForm;
import com.mysite.sbb.comment.service.ComentService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.service.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coment")
public class ComentController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ComentService comentService;

    private final UserService userService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/question/{id}")
    public String createQuestionComent(ComentForm comentForm) {
        return "comment_form";
    }


    //질문 에 댓글 달때 사용
    //
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/question/{id}")
    public String createQuestionComent(Model model, @PathVariable("id") Integer id,
                                       @Valid ComentForm comentForm, BindingResult bindingResult,
                                       Principal principal) {
        Question question = questionService.getQuestion(id);
        SiteUser user = userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        comentService.createQuestionComent(question, comentForm.getContent(), user);

        return String.format("redirect:/question/detail/%s", id);
    }

    /**
     * @param model
     * @param id
     * @param comentForm
     * @param bindingResult
     * @param principal     답변에 댓글을 다는 기능을 구현할려고 함.
     *                      구현 예정
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/answer/{id}")
    public String createAnswerComent(Model model, @PathVariable("id") Integer id,
                                     @Valid ComentForm comentForm, BindingResult bindingResult,
                                     Principal principal) {
        Answer answer = answerService.getAnswer(id);
        SiteUser user = userService.getUser(principal.getName());

        return null;
    }
}
