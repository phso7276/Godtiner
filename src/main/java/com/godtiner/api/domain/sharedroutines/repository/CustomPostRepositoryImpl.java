package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.sharedroutines.QRoutineTag;
import com.godtiner.api.domain.sharedroutines.QSharedRoutines;
import com.godtiner.api.domain.sharedroutines.RoutineTag;
import com.godtiner.api.domain.sharedroutines.SharedRoutines;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SearchCondition;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import static com.godtiner.api.domain.sharedroutines.QRoutineTag.routineTag;
import static com.godtiner.api.domain.member.QMember.member;
import static com.querydsl.core.types.Projections.constructor;
import static com.godtiner.api.domain.sharedroutines.QSharedRoutines.sharedRoutines;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

/*@Transactional(readOnly = true) // 1*/
@Repository
@Log4j2
public class CustomPostRepositoryImpl implements CustomPostRepository {
    private final JPAQueryFactory query;
    public CustomPostRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }
    @Override
    public Page<SharedRoutines> search(SearchCondition searchCondition, Pageable pageable) {
        List<OrderSpecifier> ORDERS = getAllOrderSpecifiers(pageable);

        List<SharedRoutines> content = query.selectFrom(sharedRoutines)

                .where(
                        contentHasStr(searchCondition.getRoutineContent()),
                        titleHasStr(searchCondition.getTitle())
                )
                .leftJoin(sharedRoutines.writer, member)
                //.leftJoin(sharedRoutines.routineTags, routineTag)
                //.on
                .fetchJoin()
                //.orderBy(sharedRoutines.regDate.desc())//최신 날짜부터
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                //.orderBy(sharedRoutines.likecnt.desc())
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



    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier> ORDERS = new ArrayList<>();

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                log.info(direction);

                switch (order.getProperty()) {
                    case "regdate":
                        log.info("regdate sort");
                        OrderSpecifier<?> regdate = QueryDslUtil
                                .getSortedColumn(direction, sharedRoutines, "regdate");
                        ORDERS.add(regdate);
                        break;
                    case "likecnt":
                        log.info("likecnt sort");
                        OrderSpecifier<?> likecnt = QueryDslUtil
                                .getSortedColumn(direction, sharedRoutines, "likecnt");
                        ORDERS.add(likecnt);
                        break;
                    case "pickcnt":
                        log.info("pickcnt sort");
                        OrderSpecifier<?> pickcnt = QueryDslUtil
                                .getSortedColumn(direction, sharedRoutines, "pickcnt");
                        ORDERS.add(pickcnt);
                        break;
                    default:
                        break;
                }
            }
        }

        return ORDERS;
    }
}
