package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.Article;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomArticleResponse {
    Long id;
    String title;

    public CustomArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
    }
}
