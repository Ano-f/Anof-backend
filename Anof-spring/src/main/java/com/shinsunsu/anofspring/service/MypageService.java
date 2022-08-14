package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.DislikeProduct;
import com.shinsunsu.anofspring.domain.PointDetail;
import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.response.CustomAllergyResponse;
import com.shinsunsu.anofspring.dto.response.PointDetailResponse;
import com.shinsunsu.anofspring.exception.mypage.DangerIngredientException;
import com.shinsunsu.anofspring.repository.DislikeProductRepository;
import com.shinsunsu.anofspring.repository.PointDetailRepository;
import com.shinsunsu.anofspring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class MypageService {

    @Autowired
    private DislikeProductRepository dislikeProductRepository;
    @Autowired
    private PointDetailRepository pointDetailRepository;

    //위험 성분 분석
    @Transactional(readOnly = true)
    public Map<String, Integer> getDangerIngredient(User user) {
        String[] customAllergy = {"wheat", "milk", "buckwheat", "peanut", "soybean", "mackerel", "crab", "shrimp", "pork", "peach", "tomato",
                "walnut", "chicken", "beef", "squid", "shellfish", "egg"};

        List<DislikeProduct> dislikeProductList =  dislikeProductRepository.findByUserAndIsSelect(user, 1);
        if (dislikeProductList.size() < 10) throw new DangerIngredientException("분석하기에 비선호 식품 개수가 부족합니다.");

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
                else if(a==1) dangerIngredientCount.replace(customAllergy[i], dangerIngredientCount.get(customAllergy[i])+1);
                i++;
            }
        }

        //value 기준으로 정렬
        List<String> listKeySet = new ArrayList<>(dangerIngredientCount.keySet());
        Collections.sort(listKeySet, (value1, value2) -> (dangerIngredientCount.get(value2).compareTo(dangerIngredientCount.get(value1))));

        for(String key : listKeySet) {
            System.out.println("key : " + key + " , " + "value : " + dangerIngredientCount.get(key));
        }

        //count 결과 3미만인 애들은 버리기
        for(String key : listKeySet) {
            if(dangerIngredientCount.get(key)<3) dangerIngredientCount.remove(key);
        }

        return dangerIngredientCount;

    }

    //포인트 적립 내역
    @Transactional(readOnly = true)
    public List<PointDetailResponse> getPointDetail(User user) {
        List<PointDetail> pointDetailList = pointDetailRepository.findByUser(user);
        List<PointDetailResponse> pointDetailResponseList = new ArrayList<>();

        for(PointDetail pointDetail : pointDetailList) {
            pointDetailResponseList.add(PointDetailResponse.pointDetailResponse(pointDetail));
        }
        
        Collections.reverse(pointDetailResponseList);

        return pointDetailResponseList;

    }
}
