package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.dto.SearchProductDto;
import com.shinsunsu.anofspring.dto.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByBarcode(String barcode);
    boolean existsByName(String name);
    Product findByBarcode(String barcode);
    Product findByName(String name);

//    @Query("select new com.shinsunsu.anofspring.dto.SearchProductDto(p.name, p.brand, p.image) from Product p where p.name like %:keyword%")
//    List<Product> search(@Param("keyword") String keyword);

    List<Product> findByNameContaining(String keyword);

}
