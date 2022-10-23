package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.RegisterProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterProductRepository extends JpaRepository<RegisterProduct, Long> {

    List<RegisterProduct> findByEnable(int i);
    List<RegisterProduct> findByBarcode(String barcode);
}
