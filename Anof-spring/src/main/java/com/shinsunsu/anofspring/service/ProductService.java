package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.exception.product.ProductException;
import com.shinsunsu.anofspring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true) //인식한 바코드 식품이 식품 db에 있는지 확인
    public boolean checkBarcodeExist(String barcode) {
        return productRepository.existsByBarcode(barcode);
    }
    @Transactional(readOnly = true) //인식한 바코드 식품 상세 정보 제공
    public Product detailProductByBarcode(String barcode) {

        return productRepository.findByBarcode(barcode)
                .orElseThrow(()-> {throw new ProductException("해당 상품을 찾을 수 없습니다.");
                });
    }

    @Transactional(readOnly = true) //검색된 식품명이 식품 db에 있는지 확인
    public boolean checkNameExist(String name) {
        return productRepository.existsByName(name);
    }

    @Transactional(readOnly = true) //식품명 검색을 통한 상세 정보 제공
    public Product detailProductByName(String name) {

        return productRepository.findByName(name)
                .orElseThrow(()-> {throw new ProductException("해당 상품을 찾을 수 없습니다.");
                });
    }

}
