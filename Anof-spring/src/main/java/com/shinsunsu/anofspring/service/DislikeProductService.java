package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.DislikeProduct;
import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.repository.DislikeProductRepository;
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
public class DislikeProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private DislikeProductRepository dislikeProductRepository;

    @Transactional
    public boolean dislikeProduct(String name, String id) {
        Product product = productRepository.findByName(name);
        User user = userRepository.findByUserId(id).orElseThrow();
        DislikeProduct dislikeProduct = dislikeProductRepository.findByProductAndUser(product, user);

        if(dislikeProduct==null) {
            DislikeProduct newDislikeProduct = new DislikeProduct(0, user, product);
            dislikeProductRepository.save(newDislikeProduct);
            return true;
        }
        if(dislikeProduct.getIsDelete()==1) {
            dislikeProduct.setIsDelete(0);
            return true;
        }
        dislikeProduct.setIsDelete(1);
        return true;
    }

    @Transactional(readOnly=true)
    public List<ProductResponse> listDislikeProduct(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        List<DislikeProduct> dislikeProducts = dislikeProductRepository.findByUserAndIsDelete(user, 0);
        List<ProductResponse> dislikeProductList = new ArrayList<>();

        for (DislikeProduct dislikeProduct : dislikeProducts) {
            dislikeProductList.add(ProductResponse.productResponse(dislikeProduct.getProduct()));
        }

        return dislikeProductList;
    }
}
