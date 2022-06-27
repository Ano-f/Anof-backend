package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.dto.SearchProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByBarcode(String barcode);
    boolean existsByName(String name);
    Optional<Product> findByBarcode(String barcode);
    Optional<Product> findByName(String name);

    @Query("select new com.shinsunsu.anofspring.dto.SearchProductDto(p.name, p.brand, p.image) from Product p where p.name like %:keyword%")
    List<SearchProductDto> search(@Param("keyword") String keyword);

}
