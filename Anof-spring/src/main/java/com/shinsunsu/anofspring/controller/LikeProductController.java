package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.service.LikeProductService;
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
@RequestMapping("/likeproduct")
public class LikeProductController {

    @Autowired private LikeProductService likeProductService;

    @GetMapping("/{productId}")
    public ResponseEntity saveLikeProduct(@PathVariable Long productId, Principal principal){
        return new ResponseEntity(likeProductService.likeProduct(productId, principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<List<ProductResponse>> listLikeProduct(Principal principal) {
        System.out.println(principal.getName());
        return new ResponseEntity<>(likeProductService.listLikeProduct(principal.getName()), HttpStatus.OK);
    }
}
