package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.RegisterProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterProductRepository extends JpaRepository<RegisterProduct, Long> {
}
