package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.ARTICLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ARTICLE, Long> {

}
