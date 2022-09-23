package com.shinsunsu.anofspring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class RegisterProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false, length = 5000)
    private String product_image;

    @Column(nullable = false, length = 100)
    private String barcode;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int enable;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
