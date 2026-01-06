package com.example.demo.domain.store.service;

import com.example.demo.domain.store.entity.SeoulShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeoulShopRepository extends JpaRepository<SeoulShop, Long> {
    Optional<SeoulShop> findById(Long id);
}
