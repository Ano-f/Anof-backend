package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.DislikeProduct;
import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.exception.product.ProductException;
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

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DislikeProductRepository dislikeProductRepository;

    @Transactional
    public boolean dislikeProduct(Long productId, String id) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("존재하지 않는 상품번호 입니다."));
        User user = userRepository.findByUserId(id).orElseThrow();
        DislikeProduct dislikeProduct = dislikeProductRepository.findByProductAndUser(product, user);

        if(dislikeProduct==null) {
            DislikeProduct newDislikeProduct = new DislikeProduct(1, user, product);
            dislikeProductRepository.save(newDislikeProduct);
            return true;
        }
        if(dislikeProduct.getIsSelect()==0) {
            dislikeProduct.setIsSelect(1);
            return true;
        }
        dislikeProduct.setIsSelect(0);
        return true;
    }

    @Transactional(readOnly=true)
    public List<ProductResponse.productResponse> listDislikeProduct(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        List<DislikeProduct> dislikeProducts = dislikeProductRepository.findByUserAndIsSelect(user, 1);
        List<ProductResponse.productResponse> dislikeProductList = new ArrayList<>();

        for (DislikeProduct dislikeProduct : dislikeProducts) {
            dislikeProductList.add(new ProductResponse.productResponse(dislikeProduct.getProduct()));
        }

        return dislikeProductList;
    }
}
