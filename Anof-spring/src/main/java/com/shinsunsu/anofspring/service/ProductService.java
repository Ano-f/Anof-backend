package com.shinsunsu.anofspring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinsunsu.anofspring.domain.Product;

import com.shinsunsu.anofspring.domain.RegisterProduct;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.FlaskDto;
import com.shinsunsu.anofspring.dto.request.PointRequest;
import com.shinsunsu.anofspring.dto.request.ProductRequest;
import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.exception.product.ProductException;
import com.shinsunsu.anofspring.exception.user.UserNotFoundException;
import com.shinsunsu.anofspring.repository.PointDetailRepository;
import com.shinsunsu.anofspring.repository.ProductRepository;
import com.shinsunsu.anofspring.repository.RegisterProductRepository;
import com.shinsunsu.anofspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RegisterProductRepository registerProductRepository;
    private final PointDetailRepository pointDetailRepository;

    //식품Id로 식품이 db에 있는지 확인
    @Transactional(readOnly = true)
    public boolean checkProductIdExist(Long productId) {
        return productRepository.existsById(productId);
    }

    //식품명 검색을 통한 상세 정보 제공
    @Transactional(readOnly = true)
    public Product detailProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("존재하지 않는 상품 번호입니다"));
    }

    //상품명 검색 -> 상품 리스트 제공
    @Transactional(readOnly = true)
    public List<ProductResponse.productResponse> search(String keyword) {

        List<Product> products = productRepository.findByNameContaining(keyword)
                .orElseThrow(() -> new ProductException("상품이 존재하지 않습니다."));

        List<ProductResponse.productResponse> productList = new ArrayList<>();

        for (Product product : products) {
            productList.add(new ProductResponse.productResponse(product));
        }
        return productList;
    }

    //상품 등록 요청
    @Transactional
    public boolean registerProduct(ProductRequest.registerProductRequest request, String userId) {

        if(!productRepository.existsByBarcode(request.getBarcode())) {
            User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));
            registerProductRepository.save(ProductRequest.registerProductRequest.RegisterProduct(request, user));
            return true;
        }
        else return false;
    }

    //상품 등록 + 포인트 적립
    @Transactional
    public boolean addProduct(ProductRequest.addProductRequest request) {
        Product product = productRepository.save(ProductRequest.addProductRequest.addProduct(request));
        List<RegisterProduct> registerProductList = registerProductRepository.findByBarcode(request.getBarcode());
        for (RegisterProduct registerProduct : registerProductList) {

            User user = registerProduct.getUser();
            user.setPoint(user.getPoint() + 5);

            if(user.getRanking()!=1) {
                List<User> topUsers = userRepository.findByHighRanking(user.getRanking());
                User topUser = topUsers.get(0);
                if(user.getPoint()>topUser.getPoint()) {
                    Long ranking = user.getRanking();
                    user.setRanking(topUser.getRanking());
                    for(User user1 : topUsers) {
                        user1.setRanking(ranking);
                    }
                }
                else if (user.getPoint()==topUser.getPoint()) {
                    List<User> sameRankedUsers = userRepository.findByRanking(user.getRanking());
                    sameRankedUsers.remove(user);
                    if(!sameRankedUsers.isEmpty()) {
                        for(User user1 : sameRankedUsers) {
                            user1.setRanking(user.getRanking()+1);
                        }
                    }
                    user.setRanking(topUser.getRanking());
                }
                else {
                    List<User> sameRankedUsers = userRepository.findByRanking(user.getRanking());
                    sameRankedUsers.remove(user);
                    if(!sameRankedUsers.isEmpty()) {
                        for(User user1 : sameRankedUsers) {
                            user1.setRanking(user.getRanking()+1);
                        }
                    }
                }
            }
            else {
                List<User> sameRankedUsers = userRepository.findByRanking(1L);
                sameRankedUsers.remove(user);
                if(!sameRankedUsers.isEmpty()) {
                    for(User user1 : sameRankedUsers) {
                        user1.setRanking(user.getRanking()+1);
                    }
                }
            }

            pointDetailRepository.save(PointRequest.PointDetailRequest(user, product, 5));
            registerProduct.setEnable(0);
        }

        return true;
    }

    //맞춤 정보
    @Transactional
    public Map<String, Object> customInfo(Map<String, String> map, String userId) {
        String[] customAllergy = {"wheat", "milk", "buckwheat", "peanut", "soybean", "mackerel", "crab", "shrimp", "pork", "peach", "tomato",
                "walnut", "chicken", "beef", "squid", "shellfish", "egg"};
        String[] customIngredient = {"natrium", "carbohydrates", "sugar", "fat", "trans_fat", "saturated_fat", "cholesterol", "protein",
                "calorie"};

        List<Integer> productAllergy = new ArrayList<>();
        List productIngredient = new ArrayList<>();
        Product product = new Product();
        if (map.containsKey("barcode")) {
            if (!productRepository.existsByBarcode(map.get("barcode"))) {
                throw new ProductException("존재하지 않는 상품입니다");
            }
            productAllergy = productRepository.findAllergyByBarcode(map.get("barcode")).CustomAllergy();
            productIngredient = productRepository.findIngredientByBarcode(map.get("barcode")).CustomIngredient();
            product = productRepository.findByBarcode(map.get("barcode"));
        }
        if (map.containsKey("name")) {
            productAllergy = productRepository.findAllergyByName(map.get("name")).CustomAllergy();
            productIngredient = productRepository.findIngredientByName(map.get("name")).CustomIngredient();
            product = productRepository.findByName(map.get("name"));
        }

        List<Integer> userAllergy = userRepository.findAllergy(userId).CustomAllergy();
        Map<String, Integer> allergy = new HashMap<>();

        int i = 0;
        for(int k : productAllergy) {
            if(k == 1) allergy.put(customAllergy[i],0);
            i++;
        }

        i = 0;
        for (int a : userAllergy) {
            if (a == 1) allergy.replace(customAllergy[i], 1);
            i++;
        }

        List<Integer> userIngredient = userRepository.findIngredient(userId).CustomIngredient();

        Map<String, String> ingredient = new HashMap<>();

        i = 0;
        for (int a : userIngredient) {
            if (a == 1) ingredient.put(customIngredient[i], (String) productIngredient.get(i));
            i++;
        }

        Map<String, Object> custom = new HashMap<>();
        custom.put("Id", product.getId());
        custom.put("name", product.getName());
        custom.put("brand", product.getBrand());
        custom.put("image", product.getImage());
        custom.put("allergy", allergy);
        custom.put("ingredient", ingredient);

        return custom;
    }

    //식품 추천
    @Transactional
    public List recommend(String userId) throws JsonProcessingException {
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // Object Mapper를 통한 JSON 바인딩
        FlaskDto.request flaskDto = new FlaskDto.request();
        System.out.println(userId);
        flaskDto.setUserId(userRepository.findIdByUserId(userId));
        ObjectMapper objectMapper = new ObjectMapper();
        String params = objectMapper.writeValueAsString(flaskDto);

        // HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(params, httpHeaders);

        // RestTemplate의 exchange 메소드를 통해 URL에 HttpEntity와 함께 요청
        RestTemplate restTemplate = new RestTemplate();

        //http://52.79.134.110:5005/recommend
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://15.165.237.102:5005/recommend", HttpMethod.POST,
                entity, String.class);

        String str = responseEntity.getBody();
        List<ProductResponse.productResponse> productList = new ArrayList<>();

        if(str.equals("0")) {
            for (Product product : productRepository.findProductByRandom()){
                productList.add(new ProductResponse.productResponse(product));
            }
        }
        else{
            String[] productId_arr = str.split(",");
            for (String productId : productId_arr) {
                productList.add(new ProductResponse.productResponse(productRepository.findById(Long.parseLong(productId))
                        .orElseThrow(() -> new ProductException("존재하지 않는 상품입니다"))));
            }
        }
        return productList;
    }

}
