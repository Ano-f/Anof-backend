package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.domain.Article;
import com.shinsunsu.anofspring.dto.request.ArticleRequest;
import com.shinsunsu.anofspring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/newarticle")
    public ResponseEntity<Object> addArticle(ArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setContent(article.getContent());
        article.setUrl(article.getUrl());

        List<String> keywords = articleService.searchKeywords(article.getContent());
        for(String keyword : keywords) {
            System.out.println(keyword);
        }
        return null;
        //ArticleRequest.addNewArticle( )
    }
}
