package com.core.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.api.Product;

@Repository
public interface ProdRepo extends JpaRepository<Product, Integer> {

}
