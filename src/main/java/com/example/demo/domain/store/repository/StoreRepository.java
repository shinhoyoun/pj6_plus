package com.example.demo.domain.store.repository;

import com.example.demo.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> , StoreRepositoryCustom{

    Optional<Store> findById(Long storeId);

    @Query("""
    SELECT s
    FROM Store s
    WHERE s.isDeleted = false
      AND (:totalRating IS NULL OR s.totalRating = :totalRating)
      AND (:status IS NULL OR s.status = :status)
""")
    List<Store> findStores(
            @Param("totalRating") Integer totalRating,
            @Param("status") String status
    );

    @Query("""
    SELECT s
    FROM Store s
    WHERE s.isDeleted = false
      AND (:totalRating IS NULL OR s.totalRating = :totalRating)
      AND (:status IS NULL OR s.status = :status)
""")
        // 반환타입을 Page로 수정
    Page<Store> findStoresPage(
            @Param("totalRating") Integer totalRating,
            @Param("status") String status,
            Pageable pageable
    );

}
