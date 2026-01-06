package com.example.demo.domain.store.repository;

import com.example.demo.domain.store.entity.SeoulShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeoulShopRepository extends JpaRepository<SeoulShop, Long> {
}
