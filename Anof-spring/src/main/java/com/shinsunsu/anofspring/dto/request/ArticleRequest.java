package com.shinsunsu.anofspring.dto.request;

import com.shinsunsu.anofspring.domain.Article;
import lombok.Getter;

@Getter
public class ArticleRequest {

    String title;
    String content;
    String url;
//    String keyword1;
//    String keyword2;
//    String keyword3;

    public static Article newArticle(ArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(article.getTitle());
        article.setContent(article.getContent());
        article.setUrl(article.getUrl());

        return article;
    }
//
//    public static Article addNewArticle(ArticleRequest articleRequest) {
//        Article article = new Article();
//        article.setTitle(article.getTitle());
//        article.setContent(article.getContent());
//        article.setUrl(article.getUrl());
//        article.setKeyword1(articleRequest.getKeyword1());
//        article.setKeyword2(articleRequest.getKeyword2());
//        article.setKeyword3(articleRequest.getKeyword3());
//
//        return article;
//    }
}
