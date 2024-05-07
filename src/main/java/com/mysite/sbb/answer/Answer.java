package com.mysite.sbb.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysite.sbb.comment.Coment;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.recommand.AnswerReco;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser author;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;



    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @JsonIgnore
    @OneToMany(mappedBy = "answer" ,cascade = CascadeType.REMOVE)
    private List<AnswerReco> answerRecoList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "answer" ,cascade = CascadeType.REMOVE)
    private List<Coment> comentList = new ArrayList<>();

    public void update(String content){
        this.content = content;
        modifyDate = LocalDateTime.now();
    }

}
