package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
