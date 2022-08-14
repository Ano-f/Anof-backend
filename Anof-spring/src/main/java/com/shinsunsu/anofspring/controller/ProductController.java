package com.shinsunsu.anofspring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.request.RegisterProductRequest;
import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.service.ProductService;
import com.shinsunsu.anofspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private UserService userService;


    //식품 상세 조회
    @GetMapping("/detail/{productId}")
    public ResponseEntity<Object> detailProduct(@PathVariable Long productId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        if(!productService.checkProductIdExist(productId)) { //인식한 바코드 식품 db에 존재하지 않을 경우
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProductResponse.productDetailResponse(productService.detailProductByProductId(productId)), HttpStatus.OK);
    }

    //키워드로 식품 리스트 검색
    //상품Id, 상품명, 이미지, type, brand 제외 모두 null로 가는데 product 전체 정보를 다 보내고 프론트에서 뽑아쓰는게 좋은지
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<ProductResponse.productResponse>> searchByKeyword(@PathVariable String keyword, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(productService.search(keyword), HttpStatus.OK);
    }

    //상품 등록 요청
    @PostMapping("/requestProduct")
    public ResponseEntity<Object> registerProduct(@RequestBody RegisterProductRequest request, Principal principal) {
        return new ResponseEntity<>(productService.registerProduct(request, principal.getName()), HttpStatus.OK);
    }

    //맞춤 정보 제공
    @PostMapping("/custom")
    public ResponseEntity<Object> customInfo(@RequestBody Map<String, String> map, Principal principal) {
        return new ResponseEntity<>(productService.customInfo(map, principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/recommend") //get으로 바꾸기 가능?
    public ResponseEntity<Object> recommend(Principal principal) throws JsonProcessingException {
        System.out.println(principal.getName());
        return new ResponseEntity<>(productService.recommend(principal.getName()), HttpStatus.OK);
    }

}
