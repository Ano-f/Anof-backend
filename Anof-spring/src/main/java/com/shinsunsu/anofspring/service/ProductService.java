package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.Product;

import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.request.RegisterProductRequest;
import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.exception.product.ProductException;
import com.shinsunsu.anofspring.repository.ProductRepository;
import com.shinsunsu.anofspring.repository.RegisterProductRepository;
import com.shinsunsu.anofspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RegisterProductRepository registerProductRepository;

    //@Value("${flask.url}")
    //private final String url;

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

    @Transactional //상품 등록
    public boolean registerProduct(RegisterProductRequest request, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        registerProductRepository.save(RegisterProductRequest.RegisterProduct(request, user));
        return true;
    }

    @Transactional //맞춤 정보
    public Map<String, Object> customInfo(Map<String, String> map, String userId) {
        String[] customAllergy = {"wheat", "milk", "buckwheat", "peanut", "soybean", "mackerel", "crab", "shrimp", "pork", "peach", "tomato",
                "walnut", "chicken", "beef", "squid", "shellfish", "egg"};
        String[] customIngredient = {"natrium", "carbohydrates", "sugar", "fat", "trans_fat", "saturated_fat", "cholesterol", "protein",
                "calorie"};

        List<Integer> productAllergy = new ArrayList<>();
        List<String> productIngredient = new ArrayList<>();

        if(map.containsKey("barcode")) {
            if(!productRepository.existsByBarcode(map.get("barcode"))) {throw new ProductException("존재하지 않는 상품입니다");}
            productAllergy = productRepository.findAllergyByBarcode(map.get("barcode")).CustomAllergy();
            productIngredient = productRepository.findIngredientByBarcode(map.get("barcode")).CustomIngredient();
        }
        if(map.containsKey("name")) {
            productAllergy = productRepository.findAllergyByName(map.get("name")).CustomAllergy();
            productIngredient = productRepository.findIngredientByName(map.get("name")).CustomIngredient();
        }

        List<Integer> userAllergy = userRepository.findAllergy(userId).CustomAllergy();

        Map<String, Integer> allergy = new HashMap<>();

        int i = 0;
        for(int a : userAllergy) {
            if(a==1) {
                allergy.put(customAllergy[i], productAllergy.get(i));
            }
            i++;
        }

        List<Integer> userIngredient = userRepository.findIngredient(userId).CustomIngredient();

        Map<String, String> ingredient = new HashMap<>();

        i= 0;
        for(int a : userIngredient) {
            if(a==1) {
                ingredient.put(customIngredient[i], productIngredient.get(i));
            }
            i++;
        }

        Map<String, Object> custom = new HashMap<>();
        custom.put("allergy", allergy);
        custom.put("ingredient", ingredient);

        return custom;
    }
}
