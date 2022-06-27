package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.dto.SearchProductDto;
import com.shinsunsu.anofspring.exception.product.ProductException;
import com.shinsunsu.anofspring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //바코드로 식품이 식품 db에 있는지 확인
    @Transactional(readOnly = true)
    public boolean checkBarcodeExist(String barcode) {
        return productRepository.existsByBarcode(barcode);
    }

    //바코드로 식품 상세 정보 제공
    @Transactional(readOnly = true)
    public Product detailProductByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }

    //식품명으로 식품이 db에 있는지 확인
    @Transactional(readOnly = true)
    public boolean checkNameExist(String name) {
        return productRepository.existsByName(name);
    }

    //식품명 검색을 통한 상세 정보 제공
    @Transactional(readOnly = true)
    public Product detailProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Transactional(readOnly = true) //상품명 검색 -> 상품 리스트 제공
    public List<SearchProductDto> search(String keyword) {
        return productRepository.search(keyword);
    }
}
