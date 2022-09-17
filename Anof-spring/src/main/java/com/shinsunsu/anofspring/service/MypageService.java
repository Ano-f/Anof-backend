package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.*;
import com.shinsunsu.anofspring.dto.request.updateUserRequest;
import com.shinsunsu.anofspring.dto.response.*;
import com.shinsunsu.anofspring.exception.mypage.DangerIngredientException;
import com.shinsunsu.anofspring.repository.DislikeProductRepository;
import com.shinsunsu.anofspring.repository.FAQRepository;
import com.shinsunsu.anofspring.repository.PointDetailRepository;
import com.shinsunsu.anofspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final DislikeProductRepository dislikeProductRepository;
    private final PointDetailRepository pointDetailRepository;
    private final FAQRepository faqRepository;
    private final UserRepository userRepository;
    //private final RedisTemplate redisTemplate;

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

    //랭킹
    @Transactional(readOnly = true)
    public Map<String, Object> getRanking(User user) {
        List<User> userList = userRepository.findTop50ByOrderByRanking();
        List<UserResponse.rankingResponse> rankingResponseList = new ArrayList<>();

        for(User topUser : userList) {
            rankingResponseList.add(new UserResponse.rankingResponse(topUser));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("ranking", rankingResponseList);
        map.put("user", new UserResponse.rankingResponse(user));

        return map;
    }

    /*
    //랭킹
    @Transactional(readOnly = true)
    public Map<String, Object> getRanking(User user) {

        Map<String, Object> map = new HashMap<>();

        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

        Set<String> allRankReverseSet = zSetOps.reverseRange("ranking", 0, -1);
        Iterator<String> allIter = allRankReverseSet.iterator();
        List<UserResponse.rankingResponse> list = new ArrayList<>(50);

        while(allIter.hasNext()) {
            String nickname = allIter.next();
            double point = zSetOps.score("ranking", nickname);
            List samePointRank = zSetOps.reverseRangeByScore("ranking", point, point, 0, 1).stream().collect(Collectors.toList());
            Long ranking = zSetOps.reverseRank("ranking", samePointRank.get(0));

            int i = 0;
            if (i<50) {
                list.add(new UserResponse.rankingResponse(ranking+1, nickname, (int)point));
                i++;
            }

            if(nickname.equals(user.getNickname())) {
                map.put("user", new UserResponse.rankingResponse(ranking+1, nickname, (int)point));
            }
        }

        map.put("ranking", list);

        return map;
    }


     */
}
