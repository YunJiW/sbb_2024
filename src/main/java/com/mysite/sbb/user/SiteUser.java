package com.mysite.sbb.user;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.recommand.QuestionReco;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class SiteUser {


    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;


    @OneToMany(mappedBy = "author")
    List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<QuestionReco> questionRecoList = new ArrayList<>();
    @OneToMany(mappedBy = "author")
    List<Answer> answerList = new ArrayList<>();



}
