package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.Article;
import com.shinsunsu.anofspring.dto.response.ArticleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT new com.shinsunsu.anofspring.dto.response.ArticleResponse(a) FROM Article a order by a.id desc")
    List<ArticleResponse> findArticle();

}
