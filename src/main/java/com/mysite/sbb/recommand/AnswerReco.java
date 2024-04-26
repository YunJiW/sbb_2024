package com.mysite.sbb.recommand;

import com.mysite.sbb.answer.Answer;
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
public class AnswerReco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Answer answer;

    public AnswerReco(SiteUser user, Answer answer) {
        this.user = user;
        this.answer = answer;
    }
}
