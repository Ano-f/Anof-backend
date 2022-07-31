package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.dto.CustomAllergyDto;
import com.shinsunsu.anofspring.dto.CustomProductIngredientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByBarcode(String barcode);
    boolean existsById(Long productId);
    boolean existsByName(String name);
    Optional<Product> findById(Long productId);
    Product findByName(String name);
    Product findByBarcode(String barcode);

    Optional<List<Product>> findByNameContaining(@Param("keyword") String keyword);

    @Query("select new com.shinsunsu.anofspring.dto.CustomAllergyDto(p) from Product p where p.name = :name")
    CustomAllergyDto findAllergyByName(@Param("name") String name);

    @Query("select new com.shinsunsu.anofspring.dto.CustomAllergyDto(p) from Product p where p.barcode = :barcode")
    CustomAllergyDto findAllergyByBarcode(@Param("barcode") String barcode);

    @Query("select new com.shinsunsu.anofspring.dto.CustomProductIngredientDto(p) from Product p where p.name = :name")
    CustomProductIngredientDto findIngredientByName(@Param("name") String name);

    @Query("select new com.shinsunsu.anofspring.dto.CustomProductIngredientDto(p) from Product p where p.barcode = :barcode")
    CustomProductIngredientDto findIngredientByBarcode(@Param("barcode") String barcode);
}
