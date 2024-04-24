package com.mysite.sbb.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.recommand.AnswerReco;
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


    @JsonIgnore
    @OneToMany(mappedBy = "author")
    List<Question> questionList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<QuestionReco> questionRecoList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    List<Answer> answerList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<AnswerReco> answerRecoList = new ArrayList<>();



}
