package com.mysite.sbb.comment.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class ComentController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ComentService comentService;

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    public String createQuestionComent(Model model, @PathVariable("id") Integer id,
                                       @Valid ComentForm comentForm, BindingResult bindingResult,
                                       Principal principal)
    {
        Question question = questionService.getQuestion(id);
        SiteUser user = userService.getUser(principal.getName());

        if(bindingResult.hasErrors()){
            model.addAttribute("question",question);
            return "question_detail";
        }

        return null;
    }
}
