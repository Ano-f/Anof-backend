package com.shinsunsu.anofspring.dto.request;

import com.shinsunsu.anofspring.domain.Article;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleRequest {

    String title;
    String content;
    String url;
    String keyword1;
    String keyword2;
    String keyword3;

//    public static Article newArticle(ArticleRequest articleRequest) {
//        Article article = new Article();
//        article.setTitle(article.getTitle());
//        article.setContent(article.getContent());
//        article.setUrl(article.getUrl());
//
//        return article;
//    }
//
    public static Article NewArticle(ArticleRequest articleRequest, List<String> keywords, String summary) {
        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setContent(summary);
        article.setUrl(articleRequest.getUrl());
        article.setKeyword1(keywords.get(0));
        article.setKeyword2(keywords.get(1));
        article.setKeyword3(keywords.get(2));

        return article;
    }
}
