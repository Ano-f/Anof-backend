package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.Article;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleResponse {

    Long articleId;
    String title;
    String content;
    String url;
    String keyword1;
    String keyword2;
    String keyword3;

    public ArticleResponse(Article article) {
        this.articleId = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.url = article.getUrl();
        this.keyword1 = article.getKeyword1();
        this.keyword2 = article.getKeyword2();
        this.keyword3 = article.getKeyword3();
    }
}
