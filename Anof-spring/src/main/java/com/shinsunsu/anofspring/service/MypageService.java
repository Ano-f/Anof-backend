package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.DislikeProduct;
import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.response.CustomAllergyResponse;
import com.shinsunsu.anofspring.exception.mypage.DangerIngredientException;
import com.shinsunsu.anofspring.repository.DislikeProductRepository;
import com.shinsunsu.anofspring.repository.ProductRepository;
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
public class MypageService {

    @Autowired
    private DislikeProductRepository dislikeProductRepository;
    @Autowired
    private ProductRepository productRepository;

    //위험 성분 분석
    @Transactional(readOnly = true)
    public Map<String, Integer> getDangerIngredient(User user) {
        String[] customAllergy = {"wheat", "milk", "buckwheat", "peanut", "soybean", "mackerel", "crab", "shrimp", "pork", "peach", "tomato",
                "walnut", "chicken", "beef", "squid", "shellfish", "egg"};

        List<DislikeProduct> dislikeProductList =  dislikeProductRepository.findByUserAndIsSelect(user, 1);
        if (dislikeProductList.size() < 7) throw new DangerIngredientException("분석하기에 비선호 식품 개수가 부족합니다.");

        List<Product> products = new ArrayList<>();
        for (DislikeProduct dislikeProduct : dislikeProductList) {
            products.add(dislikeProduct.getProduct());
        }
        Map<String, Integer> dangerIngredientCount = new HashMap<>();
        for(Product product : products) {
            List<Integer> productAllergy = new CustomAllergyResponse(product).CustomAllergy();
            int i = 0;
            for(int a: productAllergy){
                if(a==1 & dangerIngredientCount.get(customAllergy[i]) == null) dangerIngredientCount.put(customAllergy[i], 1);
                else if(a==1){
                    System.out.println("현재 알러지: " +customAllergy[i]);
                    dangerIngredientCount.replace(customAllergy[i], dangerIngredientCount.get(customAllergy[i])+1);
                }
                i++;
            }
            System.out.println("총 milk:" +dangerIngredientCount.get("milk"));
//            for (int a=0; a<productAllergy.size();a++) {
//                System.out.println("1: "+customAllergy[i]);
//                if (productAllergy.get(a).equals(1) & (dangerIngredientCount.get(customAllergy[i]) == null)) {
//                    System.out.println(customAllergy[i]);
//                    dangerIngredientCount.put(customAllergy[i], 1);
//                    System.out.println(dangerIngredientCount.get(customAllergy[i]));
//                }
//
//            }

            //count 결과 3이하인 애들은 버리기 + value 기준으로 정렬

        }
        return dangerIngredientCount;

    }
}
