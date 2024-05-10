package com.mysite.sbb.user.controller;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.service.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserCreateForm;
import com.mysite.sbb.user.UserPasswordFindForm;
import com.mysite.sbb.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    private final QuestionService questionService;

    private final AnswerService answerService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        log.info("회원가입 진행");
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            log.error("중복 회원가입 진행 오류");
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");

            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        log.info("회원가입 완료");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public String me(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                     @RequestParam(value = "page2", defaultValue = "0") int page2, Principal principal) {
        SiteUser user = userService.getUser(principal.getName());

        Page<Question> paging = questionService.getUserQuestionList(page, user.getId());
        Page<Answer> answerPage = answerService.getUserAnswerList(page2, user.getId());
        model.addAttribute("SiteUser", user);
        model.addAttribute("paging", paging);
        model.addAttribute("answerPaging", answerPage);
        log.info("내정보 화면 실행");
        return "member_me";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/find")
    public String findPw(UserPasswordFindForm userPasswordFindForm) {
        return "pw_find";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/find")
    public String findPw(Model model, UserPasswordFindForm userPasswordFindForm
            , BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/user/find";
        }

        SiteUser user = userService.getUser(userPasswordFindForm.getUsername());
        if (user == null) {
            bindingResult.reject("UserNotFound", "일치하는 사용자가 없습니다.");
            return "/user/find";
        }

        if (!user.getEmail().equals(userPasswordFindForm.getEmail())) {
            bindingResult.reject("EmailNotCorrect", "이메일이 일치하지않습니다.");
            return "/user/find";
        }
        String tempPw = userService.SetTempPw(user);

        userService.sendEmail(user.getEmail(), user.getUsername(), tempPw);

        redirectAttributes.addFlashAttribute("successMessage", "임시 비밀번호가 이메일로 전송되엇습니다.");
        return "redirect:/user/login";
    }

}
