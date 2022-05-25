package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByBarcode(String barcode);

    boolean existsByName(String name);
    Optional<Product> findByBarcode(String barcode);
    Optional<Product> findByName(String name);
}
