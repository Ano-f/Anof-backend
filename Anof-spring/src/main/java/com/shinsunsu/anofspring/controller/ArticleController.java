package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.domain.Article;
import com.shinsunsu.anofspring.dto.request.ArticleRequest;
import com.shinsunsu.anofspring.dto.response.ArticleResponse;
import com.shinsunsu.anofspring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/newarticle")
    public ResponseEntity<Object> addArticle(Principal principal, @RequestBody ArticleRequest articleRequest) {
        String summary = articleService.getSummary(articleRequest);
        List<String> keywords = articleService.searchKeywords(articleRequest.getContent());

        return new ResponseEntity<>(articleService.addArticle(ArticleRequest.NewArticle(articleRequest, keywords, summary)), HttpStatus.CREATED);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> getArticle(Principal principal, @PathVariable Long articleId) {
        return new ResponseEntity<>(articleService.getArticle(articleId), HttpStatus.OK);

    }
}
