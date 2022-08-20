package com.shinsunsu.anofspring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ARTICLE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false, length = 1000)
    private String keyword;

    @Column(nullable = false, length = 5000)
    private String url;
}
