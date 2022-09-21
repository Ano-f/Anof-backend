 package com.shinsunsu.anofspring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false, length = 50)
    private String keyword1;

    @Column(nullable = false, length = 50)
    private String keyword2;

    @Column(nullable = false, length = 50)
    private String keyword3;

    @Column(nullable = false, length = 5000)
    private String url;
}
