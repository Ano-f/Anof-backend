package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.LikeProduct;
import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.repository.LikeProductRepository;
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
public class LikeProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private LikeProductRepository likeProductRepository;

    @Transactional
    public Integer likeProduct(String name, String id) {
        Product product = productRepository.findByName(name);
        User user = userRepository.findByUserId(id).orElseThrow();
        LikeProduct likeProduct = likeProductRepository.findByProductAndUser(product, user);

        if(likeProduct==null) {
            LikeProduct newLikeProduct = new LikeProduct(0, user, product);
            likeProductRepository.save(newLikeProduct);
            return 1;
        }
        if(likeProduct.getIsDelete()==1) {
            likeProduct.setIsDelete(0);
            return 1;
        }
        likeProduct.setIsDelete(1);
        return 2;
    }

    @Transactional(readOnly=true)
    public List<ProductResponse> listLikeProduct(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        List<LikeProduct> likeProducts = likeProductRepository.findByUserAndIsDelete(user, 0);
        List<ProductResponse> likeProductList = new ArrayList<>();

        for (LikeProduct likeProduct : likeProducts) {
            likeProductList.add(ProductResponse.productResponse(likeProduct.getProduct()));
        }

        return likeProductList;
    }

}
