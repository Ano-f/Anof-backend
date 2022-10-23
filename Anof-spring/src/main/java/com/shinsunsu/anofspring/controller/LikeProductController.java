package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.service.LikeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/likeproduct")
public class LikeProductController {

    private final LikeProductService likeProductService;

    @PutMapping("/{productId}")
    public ResponseEntity saveLikeProduct(@PathVariable Long productId, Principal principal){
        return new ResponseEntity(likeProductService.likeProduct(productId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse.productResponse>> listLikeProduct(Principal principal) {
        return new ResponseEntity<>(likeProductService.listLikeProduct(principal.getName()), HttpStatus.OK);
    }
}
