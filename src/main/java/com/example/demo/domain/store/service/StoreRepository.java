package com.example.demo.domain.store.service;

import com.example.demo.domain.store.entity.Store;
import org.springframework.data.repository.Repository;

interface StoreRepository extends Repository<Store, Long> {

    void saveAll(Iterable<Store> stores);
}
