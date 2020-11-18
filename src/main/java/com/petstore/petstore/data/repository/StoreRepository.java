package com.petstore.petstore.data.repository;

import com.petstore.petstore.data.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
