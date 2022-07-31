package com.shinsunsu.anofspring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class DislikeProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int isSelete;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    public DislikeProduct() {}
    public DislikeProduct(int i, User user, Product product) {
        this.isSelete = i;
        this.user = user;
        this.product = product;
    }
}
