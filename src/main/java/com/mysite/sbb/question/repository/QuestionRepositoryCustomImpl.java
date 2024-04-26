package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.Question;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.mysite.sbb.question.QQuestion.question;

@Slf4j
public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Question> search(String subject, Pageable pageable) {

        List<Question> list = queryFactory
                .selectFrom(question)
                .where(question.subject.contains(subject))
                .orderBy(question.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Question> countQuery = queryFactory
                .selectFrom(question)
                .where(question.subject.contains(subject));


        log.info("한 페이지당 게시물 수 ={}",pageable.getPageSize());
        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchCount);
    }
}
