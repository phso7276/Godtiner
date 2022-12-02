package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SearchCondition;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import static com.godtiner.api.domain.member.QMember.member;
import static com.querydsl.core.types.Projections.constructor;
import static com.godtiner.api.domain.sharedroutines.QSharedRoutines.sharedRoutines;
import java.util.List;

/*@Transactional(readOnly = true) // 1*/
@Repository
public class CustomPostRepositoryImpl implements CustomPostRepository {
    private final JPAQueryFactory query;
    public CustomPostRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }
    @Override
    public Page<SharedRoutines> search(SearchCondition searchCondition, Pageable pageable) {
        List<SharedRoutines> content = query.selectFrom(sharedRoutines)

                .where(
                        contentHasStr(searchCondition.getRoutineContent()),
                        titleHasStr(searchCondition.getTitle())
                )
                .leftJoin(sharedRoutines.writer, member)

                .fetchJoin()
                .orderBy(sharedRoutines.regDate.desc())//최신 날짜부터
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); //Count 쿼리 발생 X




        JPAQuery<SharedRoutines> countQuery = query.selectFrom(sharedRoutines)
                .where(
                        contentHasStr(searchCondition.getRoutineContent()),
                        titleHasStr(searchCondition.getTitle())
                );


        return  PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    } // 2

    private BooleanExpression contentHasStr(String content) {
        return StringUtils.hasLength(content) ? sharedRoutines.routineContent.contains(content) : null;
    }


    private BooleanExpression titleHasStr(String title) {
        return StringUtils.hasLength(title) ? sharedRoutines.title.contains(title) : null;
    }

}
