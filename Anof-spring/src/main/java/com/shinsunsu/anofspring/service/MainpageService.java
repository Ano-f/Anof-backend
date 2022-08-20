package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.ARTICLE;
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
    public List<ARTICLE> getArticle(String userId) {
        List<ARTICLE> articleList = articleRepository.findAll();
        List articles = new ArrayList<>();
        for(ARTICLE article : articleList) {
            articles.add(article.getTitle());
        }
        return articles;
    }
}
