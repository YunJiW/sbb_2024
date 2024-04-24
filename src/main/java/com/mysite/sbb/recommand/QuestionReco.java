package com.mysite.sbb.recommand;


import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class QuestionReco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;
}
