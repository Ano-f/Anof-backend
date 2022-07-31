package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.LikeProduct;
import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeProductRepository extends JpaRepository<LikeProduct, Long> {
    LikeProduct findByProductAndUser(Product product, User user);

    List<LikeProduct> findByUserAndIsSelect(User user, int i);
}
