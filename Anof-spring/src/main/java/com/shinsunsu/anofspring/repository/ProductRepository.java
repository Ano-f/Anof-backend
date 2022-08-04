package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.dto.response.CustomAllergyResponse;
import com.shinsunsu.anofspring.dto.response.CustomProductIngredientResponse;
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

    @Query("select new com.shinsunsu.anofspring.dto.response.CustomAllergyResponse(p) from Product p where p.name = :name")
    CustomAllergyResponse findAllergyByName(@Param("name") String name);

    @Query("select new com.shinsunsu.anofspring.dto.response.CustomAllergyResponse(p) from Product p where p.barcode = :barcode")
    CustomAllergyResponse findAllergyByBarcode(@Param("barcode") String barcode);

    @Query("select new com.shinsunsu.anofspring.dto.response.CustomProductIngredientResponse(p) from Product p where p.name = :name")
    CustomProductIngredientResponse findIngredientByName(@Param("name") String name);

    @Query("select new com.shinsunsu.anofspring.dto.response.CustomProductIngredientResponse(p) from Product p where p.barcode = :barcode")
    CustomProductIngredientResponse findIngredientByBarcode(@Param("barcode") String barcode);
}
