package com.mysite.sbb.comment.controller;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.comment.Coment;
import com.mysite.sbb.comment.ComentForm;
import com.mysite.sbb.comment.service.ComentService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.service.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/answer/{id}")
    public String createAnswerComent(ComentForm comentForm) {
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/answer/{id}")
    public String createAnswerComent(Model model, @PathVariable("id") Integer id,
                                     @Valid ComentForm comentForm, BindingResult bindingResult,
                                     Principal principal) {
        Answer answer = answerService.getAnswer(id);
        SiteUser user = userService.getUser(principal.getName());


        if (bindingResult.hasErrors()) {
            model.addAttribute("question", answer.getQuestion());
            return "question_detail";
        }
        comentService.createAnswerComent(answer, comentForm.getContent(), user);

        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String CommentUpdate(ComentForm comentForm, @PathVariable("id") Integer id, Principal principal) {
        Coment coment = comentService.getComent(id);
        if (!coment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        comentForm.setContent(coment.getContent());

        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String CommentUpdate(@Valid ComentForm comentForm, BindingResult bindingResult, Principal principal,
                                @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }

        Coment coment = comentService.getComent(id);

        if (!coment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        comentService.modify(coment, comentForm.getContent());
        if (coment.getQuestion() == null) {
            return String.format("redirect:/question/detail/%s", coment.getAnswer().getQuestion().getId());
        } else {
            return String.format("redirect:/question/detail/%s", coment.getQuestion().getId());
        }
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("delete/{id}")
    public String ComentDelete(Principal principal, @PathVariable("id") Integer id) {
        Coment coment = comentService.getComent(id);

        if (!coment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        comentService.delete(coment);
        if (coment.getQuestion() == null) {
            return String.format("redirect:/question/detail/%s", coment.getAnswer().getQuestion().getId());
        } else {
            return String.format("redirect:/question/detail/%s", coment.getQuestion().getId());
        }
    }
}
