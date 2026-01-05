package com.example.demo.domain.store.repository;

import com.example.demo.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

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

//    List<Store> findByTotalRatingAndIsDeletedFalse(Integer totalRating);
//
//    List<Store> findByStatusAndIsDeletedFalse(String status);

}
