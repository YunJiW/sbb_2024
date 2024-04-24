package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    public void update(String content){
        this.content = content;
        modifyDate = LocalDateTime.now();
    }

}
