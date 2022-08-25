package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.*;
import com.shinsunsu.anofspring.dto.request.updateUserRequest;
import com.shinsunsu.anofspring.dto.response.*;
import com.shinsunsu.anofspring.exception.mypage.DangerIngredientException;
import com.shinsunsu.anofspring.repository.DislikeProductRepository;
import com.shinsunsu.anofspring.repository.FAQRepository;
import com.shinsunsu.anofspring.repository.PointDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final DislikeProductRepository dislikeProductRepository;
    private final PointDetailRepository pointDetailRepository;
    //private final UserRepository userRepository;

    private final FAQRepository faqRepository;

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

        //value 기준으로 공통 성분이 많이 count된 순으로 정렬
        List<String> listKeySet = new ArrayList<>(dangerIngredientCount.keySet());
        Collections.sort(listKeySet, (value1, value2) -> (dangerIngredientCount.get(value2).compareTo(dangerIngredientCount.get(value1))));

        double totalCount = dangerIngredientCount.size();
        for(String key : listKeySet) {
            if(dangerIngredientCount.get(key)<3) {
                dangerIngredientCount.remove(key);
                continue;
            }
            dangerIngredientCount.replace(key, (int) (dangerIngredientCount.get(key)/totalCount*100));
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

    //정보 수정 전 사용자 정보 확인
    @Transactional(readOnly = true)
    public UserResponse.userInfoResponse getUserInfo(User user) {
        return new UserResponse.userInfoResponse(user);
    }

    //사용자 정보 수정
    @Transactional
    public boolean updateUserInfo(updateUserRequest updateInfo, User user) {

        if (updateInfo.getAllergy() != null) {
            if (new CustomAllergyResponse(user.getAllergy()).CustomAllergy()
                    .equals(new CustomAllergyResponse(updateInfo.getAllergy()).CustomAllergy())) {
                return false;
            }
            else updateInfo.updateAllergy(user.getAllergy(), updateInfo.getAllergy());
        }

        else {
            if (new CustomUserIngredientResponse(user.getIngredient()).CustomIngredient()
                    .equals(new CustomUserIngredientResponse(updateInfo.getIngredient()).CustomIngredient())) {
                return false;
            }
            else updateInfo.updateIngredient(user.getIngredient(), updateInfo.getIngredient());
        }
        return true;
    }

    //FAQ
    @Transactional(readOnly = true)
    public List<FAQResponse> faq() {

        List<FAQ> faqList= faqRepository.findAll();
        List<FAQResponse> faqResponseList = new ArrayList<>();

        for(FAQ faq : faqList) {
            faqResponseList.add(new FAQResponse(faq));
        }
        return faqResponseList;
    }
}
