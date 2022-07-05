package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.Product;

import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.repository.ProductRepository;
import com.shinsunsu.anofspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

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

    //상품명 검색 -> 상품 리스트 제공
    @Transactional(readOnly = true)
    public List<ProductResponse> search(String keyword) {
        List<Product> products = productRepository.findByNameContaining(keyword);
        List<ProductResponse> productList = new ArrayList<>();

        for (Product product : products) {
            productList.add(ProductResponse.productResponse(product));
        }

        return productList;
    }

}
