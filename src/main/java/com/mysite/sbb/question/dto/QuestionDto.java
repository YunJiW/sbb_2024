package com.mysite.sbb.question.dto;

import com.mysite.sbb.user.SiteUser;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuestionDto {


    String subject;
    String content;

    SiteUser user;

    LocalDateTime createdDate;


    @QueryProjection
    public QuestionDto(String subject, String content, SiteUser user, LocalDateTime createdDate) {
        this.subject = subject;
        this.content = content;
        this.user = user;
        this.createdDate = createdDate;
    }
}
