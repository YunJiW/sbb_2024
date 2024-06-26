package com.mysite.sbb.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.comment.Coment;
import com.mysite.sbb.recommand.QuestionReco;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "question_id")
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser author;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<QuestionReco> recoList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "question",cascade = CascadeType.REMOVE)
    private List<Coment> comentList = new ArrayList<>();


    public Question(String subject, String content, LocalDateTime createDate, SiteUser author) {
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.author = author;
    }

    public void update(String subject, String content) {
        this.subject = subject;
        this.content = content;
        modifyDate = LocalDateTime.now();
    }
}
