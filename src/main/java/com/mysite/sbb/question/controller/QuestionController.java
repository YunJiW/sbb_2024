package com.mysite.sbb.question.controller;

import com.mysite.sbb.CommonUtil;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionForm;
import com.mysite.sbb.question.service.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final UserService userService;

    private final CommonUtil commonUtil;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "sub",defaultValue = "")String sub) {

        Page<Question> paging = questionService.getList(page,sub);

        log.info("게시물 총 개수 =  {}",paging.getTotalElements());

        model.addAttribute("paging", paging);
        model.addAttribute("sub",sub);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id,
                         AnswerForm answerForm,@RequestParam(value = "answerPage",defaultValue = "0")int answerPage) {
        Question question = questionService.getQuestion(id);

        Page<Answer> answerPaging = answerService.getList(question, answerPage);
        model.addAttribute("question", question);
        model.addAttribute("answerPaging",answerPaging);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = userService.getUser(principal.getName());
        String convertContent = commonUtil.markdown(questionForm.getContent());
        questionService.create(questionForm.getSubject(), convertContent, siteUser);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = questionService.getQuestion(id);

        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        String convertContent = commonUtil.markdown(questionForm.getContent());
        questionService.modify(question, questionForm.getSubject(), convertContent);

        return String.format("redirect:/question/detail/%s", id);


    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);

        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionService.delete(question);
        return "redirect:/";
    }
}
