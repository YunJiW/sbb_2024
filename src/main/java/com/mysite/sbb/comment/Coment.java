package com.mysite.sbb.comment;


import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Coment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    private Answer answer;

    public Coment(String content, LocalDateTime createDate) {
        this.content = content;
        this.createDate = createDate;
    }

    public void AddQuestion(Question question){
        this.question = question;
    }

    public void AddAnswer(Answer answer){
        this.answer =answer;
    }

    public void updateComent(String content){
        this.content = content;
    }

}
