package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.Article;
import com.shinsunsu.anofspring.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainpageService {

    private final ArticleRepository articleRepository;

    //맞춤 기사
    @Transactional(readOnly = true)
    public List<Article> getArticle(String userId) {
        List<Article> articleList = articleRepository.findAll();
        List articles = new ArrayList<>();
        for(Article article : articleList) {
            articles.add(article.getTitle());
        }
        return articles;
    }
}
