package com.shinsunsu.anofspring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PointDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private Product product;

    @Column(nullable = false)
    private int point;
}
