package com.example.demo.domain.store.repository;

import com.example.demo.domain.store.entity.QStore;
import com.example.demo.domain.store.entity.Store;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    // QueryDSL 쿼리를 생성해주는 팩토리
    private final JPAQueryFactory queryFactory;


    // v2
    private final BooleanExpression mainItemContains(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }

        QStore store = QStore.store;

        return store.mainItem.isNotNull()
                .and(store.mainItem.containsIgnoreCase(keyword.trim()));
    }

    // LIKE search
    @Override
    public Page<Store> findByMainItem(String keyword, Pageable pageable) {

        QStore store = QStore.store;

        List<Store> content = queryFactory
                .selectFrom(store)
                .where(mainItemContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(store.count())
                .from(store)
                .where(mainItemContains(keyword))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }






//    // v1
//    // boolean expressoin 메서드
//    public BooleanExpression mainItemContains(String keyword) {
//
//        QStore store = QStore.store;
//
//        if(keyword == null || keyword.trim().isEmpty()) {
//            return null;
//        }
//
//        return store.mainItem.isNotNull()
//                .and(store.mainItem.trim().contains(keyword.trim()));
//    }
//
//
//    /**
//     * 인기 검색어 TOP N 조회
//     */
    @Override
    public List<String> findPopularMainItems(int limit) {

        QStore store = QStore.store;

        log.info("Query - 인기검색어 조회 limit={}", limit);

        return queryFactory
                // 주요취급품목 컬럼만 조회
                .select(store.mainItem)
                // Store 테이블 기준
                .from(store)
                // null 데이터 제외
                .where(store.mainItem.isNotNull())
                // 같은 품목끼리 그룹핑
                .groupBy(store.mainItem)
                // 많이 등장한 순서 = 인기순
                .orderBy(store.mainItem.count().desc())
                // 상위 N개만 조회 (TOP 10 등)
                .limit(limit)
                // 결과 조회
                .fetch();
    }
//
//    /**
//     * 주요취급품목 검색 + 페이지네이션
//     */
//    @Override
//    public Page<Store> findByMainItem(String keyword, Pageable pageable) {
//
//        QStore store = QStore.store;
//
//        // 실제 페이지 데이터 조회
//        List<Store> content = queryFactory
//                // null일때 처리
//                // boolean expression 사용해서
//
//                .selectFrom(store)
//                // LIKE 검색 (대소문자 무시) + 공백or띄어쓰기시 처리
//                .where(mainItemContains(keyword))
//                // 페이지 시작 위치
//                .offset(pageable.getOffset())
//                // 페이지 크기
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        // 전체 데이터 개수 조회 (페이지 계산용)
//        Long total = queryFactory
//                .select(store.count())
//                .from(store)
//                .where(mainItemContains(keyword))
//                .fetchOne();
//
//        // Page 객체로 변환
//        return new PageImpl<>(
//                content,
//                pageable,
//                total == null ? 0 : total
//        );
//    }
//
//


}
