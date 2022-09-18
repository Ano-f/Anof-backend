package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.dto.request.ArticleRequest;
import com.shinsunsu.anofspring.dto.request.ProductRequest;
import com.shinsunsu.anofspring.service.AdminService;
import com.shinsunsu.anofspring.service.ArticleService;
import com.shinsunsu.anofspring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/adminFun")
public class AdminController {

    private final AdminService adminService;
    private final ArticleService articleService;
    private final ProductService productService;

    //식품 등록 요청 목록 제공
    @GetMapping("/requestProductList")
    public ResponseEntity<Object> getRegisterRequestProductList() {
        return new ResponseEntity<>(adminService.getRegisterRequestProductList(), HttpStatus.OK);
    }

    //기사 등록
    @PostMapping("/newarticle")
    public ResponseEntity<Object> addArticle(Principal principal, @RequestBody ArticleRequest articleRequest) {
        String summary = articleService.getSummary(articleRequest);
        List<String> keywords = articleService.searchKeywords(articleRequest.getContent());

        return new ResponseEntity<>(articleService.addArticle(ArticleRequest.NewArticle(articleRequest, keywords, summary)), HttpStatus.CREATED);
    }

    //식품 등록
    @PostMapping("/newProduct")
    public ResponseEntity<Boolean> addProduct(@RequestBody ProductRequest.addProductRequest request, Principal principal) {
        return new ResponseEntity<>(productService.addProduct(request), HttpStatus.OK);
    }

    //공지사항 등록


}
