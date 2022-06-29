package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.service.DislikeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class DislikeProductController {

    @Autowired
    private DislikeProductService dislikeProductService;

    @PostMapping("/like")
    public ResponseEntity<Object> saveLikeProduct(@RequestBody Map<String, String> name, Principal principal){
        return new ResponseEntity<>(dislikeProductService.dislikeProduct(name.get("name"), principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/like/list")
    public ResponseEntity<List<ProductResponse>> listLikeProduct(Principal principal) {
        return new ResponseEntity<>(dislikeProductService.listDislikeProduct(principal.getName()), HttpStatus.OK);
    }
}
