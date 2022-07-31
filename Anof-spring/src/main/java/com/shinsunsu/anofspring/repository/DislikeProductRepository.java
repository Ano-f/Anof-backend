package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.DislikeProduct;
import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DislikeProductRepository extends JpaRepository<DislikeProduct, Long> {

    DislikeProduct findByProductAndUser(Product product, User user);

    List<DislikeProduct> findByUserAndIsSelect(User user, int i);
}
