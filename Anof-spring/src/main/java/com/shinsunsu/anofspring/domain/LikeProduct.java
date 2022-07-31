package com.shinsunsu.anofspring.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class LikeProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int isSelect; //선호 식품에서 삭제한 경우 0

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    public LikeProduct() {}
    public LikeProduct(int i, User user, Product product) {
        this.isSelect = i;
        this.user = user;
        this.product = product;
    }
}
