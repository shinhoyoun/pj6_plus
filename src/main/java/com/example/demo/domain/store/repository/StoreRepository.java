package com.example.demo.domain.store.repository;

import com.example.demo.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {


    Optional<Store> findById(Long storeId);

}
