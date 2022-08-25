package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.domain.Article;
import com.shinsunsu.anofspring.dto.request.ArticleRequest;
import com.shinsunsu.anofspring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/newarticle")
    public ResponseEntity<Object> addArticle(Principal principal, @RequestBody ArticleRequest articleRequest) throws IOException {
//        Article article = new Article();
//        article.setTitle(articleRequest.getTitle());
//        article.setContent(articleRequest.getContent());
//        article.setUrl(articleRequest.getUrl());

        System.out.println(articleRequest.getContent());
        articleService.searchKeywords(articleRequest.getContent());
//        for(String keyword : keywords) {
//            System.out.println(keyword);
//        }
        return null;
        //ArticleRequest.addNewArticle( )
    }
}
