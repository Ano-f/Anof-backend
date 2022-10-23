package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.LikeProduct;
import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.exception.product.ProductException;
import com.shinsunsu.anofspring.repository.LikeProductRepository;
import com.shinsunsu.anofspring.repository.ProductRepository;
import com.shinsunsu.anofspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final LikeProductRepository likeProductRepository;

    @Transactional
    public boolean likeProduct(Long productId, String id) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("존재하지 않는 상품 번호입니다."));
        User user = userRepository.findByUserId(id).orElseThrow();
        LikeProduct likeProduct = likeProductRepository.findByProductAndUser(product, user);

        if(likeProduct==null) {
            LikeProduct newLikeProduct = new LikeProduct(1, user, product);
            likeProductRepository.save(newLikeProduct);
            return true;
        }
        if(likeProduct.getIsSelect()==0) {
            likeProduct.setIsSelect(1);
            return true;
        }
        likeProduct.setIsSelect(0);
        return true;
    }

    @Transactional(readOnly=true)
    public List<ProductResponse.productResponse> listLikeProduct(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        List<LikeProduct> likeProducts = likeProductRepository.findByUserAndIsSelect(user, 1);
        List<ProductResponse.productResponse> likeProductList = new ArrayList<>();

        for (LikeProduct likeProduct : likeProducts) {
            likeProductList.add(new ProductResponse.productResponse(likeProduct.getProduct()));
        }

        return likeProductList;
    }

}
