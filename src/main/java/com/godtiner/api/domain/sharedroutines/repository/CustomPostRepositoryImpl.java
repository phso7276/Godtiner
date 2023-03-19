package com.godtiner.api.domain.sharedroutines.repository;

import com.godtiner.api.domain.sharedroutines.*;
import com.godtiner.api.domain.sharedroutines.dto.sharedRoutines.SearchCondition;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import static com.godtiner.api.domain.member.QMember.member;
import static com.querydsl.core.types.Projections.constructor;
import static com.godtiner.api.domain.sharedroutines.QSharedRoutines.sharedRoutines;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/*@Transactional(readOnly = true) // 1*/
@Transactional(readOnly = true)
@Log4j2
public class CustomPostRepositoryImpl implements CustomPostRepository {
    private final JPAQueryFactory query;

   public CustomPostRepositoryImpl(EntityManager em)
    {
        query = new JPAQueryFactory(em);
    }
   /* public CustomPostRepositoryImpl(JPAQueryFactory query) { // 4
        super(SharedRoutines.class);
        this.query = query;
    }*/
    @PersistenceContext
    private EntityManager em;

    private final QRoutineTag routineTag = new QRoutineTag("routineTag");
    private final QSharedRoutines sharedRoutines = new QSharedRoutines("sharedRoutines");
    @Override
    public Page<SharedRoutines> search(SearchCondition searchCondition, Pageable pageable) {
        List<OrderSpecifier> ORDERS = getAllOrderSpecifiers(pageable);

        List<SharedRoutines> content = query.selectFrom(sharedRoutines)
                .leftJoin(sharedRoutines.writer, member)
                .leftJoin(sharedRoutines.routineTags,routineTag)
                .where(
                        contentHasStr(searchCondition.getRoutineContent()),
                        titleHasStr(searchCondition.getTitle()),
                       //orConditionsByEqCategoryName(searchCondition.getTagName())
                       tagHasStr(searchCondition.getTagName())
                        //tagHasStr(routineTag.tagName.contains(searchCondition.getTagName()))
                )
                .fetchJoin()
                //.orderBy(sharedRoutines.regDate.desc())//최신 날짜부터
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); //Count 쿼리 발생 X




        JPAQuery<SharedRoutines> countQuery = query.selectFrom(sharedRoutines)
                .where(
                        contentHasStr(searchCondition.getRoutineContent()),
                        titleHasStr(searchCondition.getTitle()),
                        //routineTag.tagName.contains(searchCondition.getTagName())
                       tagHasStr(searchCondition.getTagName())
                );


        return  PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    } // 2

    /*private Predicate tagHasStr(BooleanExpression contains) {
    }
*/
    public List<SharedRoutines> getSharedRoutinesByTagName(String tagName){

        JPAQuery<SharedRoutines> query2 = new JPAQuery<>(em, MySQLJPATemplates.DEFAULT);

        List<SharedRoutines> content = query2.from(sharedRoutines)
                //.leftJoin(sharedRoutines.writer, member)
                .leftJoin(sharedRoutines.routineTags,routineTag)
                .where(routineTag.tagName.contains(tagName))
                //.fetchJoin()
                //.orderBy(sharedRoutines.avgPreference.desc())
                .orderBy(NumberExpression.random().asc())
                .limit(2)
                .fetch();

        return content;

    }



    private BooleanExpression contentHasStr(String content) {
        return StringUtils.hasLength(content) ? sharedRoutines.routineContent.contains(content) : null;
    }

    private BooleanExpression titleHasStr(String title) {
        return StringUtils.hasLength(title) ? sharedRoutines.title.contains(title) : null;
    }

   private BooleanExpression tagHasStr(String tagName){
        return StringUtils.hasLength(tagName) ? sharedRoutines.routineTags.any().tagName.eq(tagName):null;
    }

    /*private BooleanExpression tagHasId(Long tagId){
        return StringUtils.hasLength(tagName) ? routineTag.tagName.contains(tagName):null;
    }*/




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
